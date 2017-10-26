### Android Infrastructure

[ ![Download](https://api.bintray.com/packages/albertopeam/android/Concurrency/images/download.svg) ](https://bintray.com/albertopeam/android/Concurrency/_latestVersion)
[![Build Status](https://travis-ci.org/albertopeam/Android-Infrastructure.svg?branch=master)](https://travis-ci.org/albertopeam/Android-Infrastructure)
[![codecov](https://codecov.io/gh/albertopeam/Android-Infrastructure/branch/master/graph/badge.svg)](https://codecov.io/gh/albertopeam/Android-Infrastructure)
[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/albertopeam/Android-Infrastructure/blob/master/LICENSE)

The intention of this library is to help developers to decouple their code
from the Android framework and simplify the execution of asynchronous code, 
providing an environment where the code is synchronous, which is more easy
to read, mantain, scale and test. The other goal is to handle exceptions 
raised during the execution of the code and handle them in an uniform way
without repeat code.


Features:
1. We avoid create threads in the middle of our code.
2. We avoid handle communication between threads.
3. We automate way of canceling tasks, linking those to the framework
component lifecycle.
4. We lead to write synchronous code that is easy to read, maintain, scale
and test.
5. We provide a way of handle exceptions without repeat code.

There are two modules in the repo, one called app and other infrastructure.
The app module shows the usage of the infrastructure module, app is
built based on MVP with the help of Dagger2 DI framework. It is a very
basic example to list, create and destroy notes.
The infrastructure module is the core of the library.


### Install
[Gradle Dependency](#dependency)

### Usage
1. [Create use case executor](#createinfra)
2. [Create exception controller](#createexceptionhandler)
3. [Create an use case](#createusecase)
4. [Connect all to the presenter and the view](#connectopresenter)

### Testing
[Test the activity](#testandroidui)


Install
-------

##### <a name="dependency">Gradle Dependency</a>

The Gradle dependency is available via jCenter.

The minimum API level supported by this library is API 15 (ICE_CREAM_SANDWICH_MR1).

Add the Google Maven repository if you are using a version of Android Studio < 3:
```groovy
allprojects {
    repositories {
        jcenter()
        maven { url 'https://maven.google.com' }
    }
}
```

Add the dependency to include Android components Lifecycle(LifecycleActivity, LifecycleFragment), this
isn't needed if you are using a version of [support library](https://developer.android.com/topic/libraries/support-library/revisions.html#26-1-0)>= 26.1.0:
```groovy
compile "android.arch.lifecycle:extensions:1.0.0-rc1"
```
For more info, check [documentation](https://developer.android.com/topic/libraries/architecture/adding-components.html)

Add infrastructure dependency:
```groovy
compile 'com.github.albertopeam:infrastructure:0.0.10-rc1'
```

Usage
-----
Follow the next steps to create a basic infrastructure to execute
asynchronous code and handle exceptions that are raised during the execution
of the operations.
Then we will wire this infrastructure
to the view and the presenter to create a complete example.


#### <a name="createinfra">1. Create use case executor</a>
The **UseCaseExecutor** object provides the ability to run **UseCase** objects
in a separate thread and when it end running invokes the Android
main thread with the result. This is done behind the scenes.
Another feature is that this **UseCaseExecutor** will handle Exceptions thrown
during the execution of the **UseCase** and report to the caller.
```java
UseCaseExecutor useCaseExecutor = UseCaseExecutorFactory.provide();
```


#### <a name="createexceptionhandler">2. Create exception controller</a>
**ExceptionController** class handles the exceptions that are thrown during
the **UseCase** execution. This will need a list of delegates as parameter,
every one will handle a concrete exception. **ExceptionDelegate**(s) are
usefull for handling exceptions without repetitives endless of "if,
else if, else" blocks. Every **ExceptionDelegate** will return a **HandledException**
that will handle the exception managed by its delegate.

The next example covers the creation of a **ExceptionDelegate** that handle a
NullPointerException, it will return a **HandledException** that  recover
for the exception, in this example we only are going to inform that there
is an internal error via log. This delegate is only a example,
its discouraged to capture RuntimeException(NullPointerException) in order
to solve program errors during the development phase.
```java
ExceptionDelegate aDelegate = new ExceptionDelegate() {
    @Override
    public boolean canHandle(Exception exception) {
        return exception instanceof NullPointerException;
    }

    @Override
    public HandledException handle(Exception exception) {
        return new HandledException() {

            @Override
            public void recover() {
                Log.d("ExceptionDelegate","FileNotFoundException");
            }
        };
    }

    @Override
    public boolean belongsTo(LifecycleOwner lifecycleOwner) {
        return false;
    }
};
List<ExceptionDelegate> delegates = new ArrayList<>();
delegates.add(aDelegate);
ExceptionController exceptionController = ExceptionControllerFactory.provide(delegates);
```



#### <a name="createusecase">3. Create an use case</a>
An **UseCase** is a piece of code that executes one or more operations and
returns a result to the caller, or if an exception was raised, inform
the caller via a **HandledException**. The **UseCase** make use of generics,
as input and output, this will impact when we add it to the
**UseCaseExecutor** and implement the **Callback** that already is linked to the
same **UseCase** generics.

We will need to pass as parameter a **ExceptionController**([like the one
that we have been created previously](#createexceptionhandler)) that is
going to handle all exceptions triggered during the **UseCase** execution;
and a **LifecycleOwner** that is not going to respond to the **Callback** in the case
that the android component be destroyed. Another case is when the **UseCase**
is not going to run if the **LifecycleOwner** in not between creation and
resumed state.

In this example we are going to inject a domain service that receives a
string and return it converted to uppercase. In this case we are only
injecting one object but we can add more and use the **UseCase** as a coordinator
between services.


Definition of the domain service:
```java
public class UpperCaseService {
    public @NonNull String convert(@NonNull String s){
        return s.toUpperCase();
    }
}
```

Definition of the **UseCase** that coordinates the service. Remember that many
services can be injected and favor composition.
```java
class UpperCaseUseCase extends UseCase<String, String >{

        private UpperCaseService upperCaseService;

        UpperCaseUseCase(@NonNull ExceptionController exceptionController,
                         @NonNull Lifecycle lifecycle,
                         @NonNull UpperCaseService upperCaseService) {
            super(exceptionController, lifecycle);
            this.upperCaseService = upperCaseService;
        }

        @Override
        protected String run(String s) throws Exception {
            return upperCaseService.convert(s);
        }
    }
```

Create the **UseCase**.
```java
Lifecycle lifecycle = activity.getLifecycle();
UpperCaseService upperCaseService = new UpperCaseService();
ExceptionController exceptionController = ExceptionControllerFactory.provide(delegates);
UpperCaseUseCase upperCaseUseCase= new UpperCaseUseCase(exceptionController, lifecycle, upperCaseService);
```



#### <a name="connectopresenter">4 Connect all to the presenter and the view</a>
The presenter will handle the view(activity) input events and the
**UseCaseExecutor** output events. We will need to inject all the dependencies
created before, in the [first](#createinfra) and [third](createusecase) steps.

In case of the code completes successfully the onSuccess method of the
**Callback** will be invoked.

If any exception is triggered in the **UseCase** the onException method of the
**Callback** will be invoked with a **HandledException**. Then we can choose to
recover from it invoking its recover method.
```java
void toUpperCase(String aString){
    useCaseExecutor.execute(aString, upperCaseUseCase, new Callback<String>(){
                @Override
                public void onSuccess(String note) {
                    view.onUpperCase(note);
                }

                @Override
                public void onException(HandledException handledException) {
                    handledException.recover();
                }
            });
}

```
**Activity** code that handles events from/toward the presenter. It only shows
a Toast with the result of the UseCase.
If you are using support library >= 26.1.0 you can extend from activity in
other case you must need to use **LifecycleActivity**
```java
public class UpperCaseActivity
        extends LifecycleActivity {

    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uppercase);
        presenter.toUpperCase("sample");
    }

    void onUpperCase(String result){
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}
```


Testing
-------

#### <a name="testandroidui">Test the activity</a>

For test all this stuff that we have built using Dagger2 we can use a library called
[DaggerMock](https://github.com/fabioCollini/DaggerMock).
This library helps us replacing all the objects provided by the Dagger2 modules,
in fact we can replace the dependencies of the subject under test with test doubles.

First of all we need to replace the first **Component** that is created in the
graph. We are assuming that the main component/module is called
**AppComponent**/**AppModule**, the **Application** class name is App.

```java
public class EspressoDaggerMockRule
        extends DaggerMockRule<AppComponent>{

    public EspressoDaggerMockRule() {
        super(AppComponent.class, new AppModule(getApp()));
        set(new DaggerMockRule.ComponentSetter<AppComponent>() {
            @Override public void setComponent(AppComponent component) {
                getApp().setAppComponent(component);
            }
        });
    }

    private static App getApp() {
        return (App) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
```

Now in the instrumentation test. We define all the mocks that we will need
in the test. Also create the ActivityTestRule but config it to avoid autorun.
Finally create the **EspressoDaggerMockRule**. With the setup complete we can
create a test and run it. All the mocks will be injected as doubles in the
Activity module.

```java
@Mock
UseCaseExecutor mockUseCaseExecutor;
@Mock
ExceptionController mocExceptionController;
@Mock
LoadNotesUseCase mockLoadNotesUseCase;
@Mock
AddNoteUseCase mockAddNoteUseCase;
@Mock
NotesViewModel mockNotesViewModel;
@Rule
public EspressoDaggerMockRule rule =
            new EspressoDaggerMockRule();
@Rule
public ActivityTestRule<NotesActivity> activityTestRule =
            new ActivityTestRule<>(NotesActivity.class, true, false);

@Test
public void givenResumedWhenLoadedNotesThenShowThenInAList() throws InterruptedException {
    final List<String> notes = new ArrayList<>();
    notes.add("a-note");
      doAnswer(new Answer() {
        @Override
        public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
            ((Callback<List<String>>)invocationOnMock.getArguments()[2]).onSuccess(notes);
            return null;
        }
    }).when(mockUseCaseExecutor).execute(
            ArgumentMatchers.<Void>any(),
            any(LoadNotesUseCase.class),
            ArgumentMatchers.<Callback<List<String>>>any());
    activityTestRule.launchActivity(null);
    onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    onView(withId(R.id.recycler)).check(RecyclerViewAssertions.hasItemsCount(1));
}
```

Contribute
-------
If you want to contribute feel free to make a PR, here are some of the
future taks:
*  review packaging
*  Code examples in kotlin
*  remove testCoverageEnabled from app module to avoid generate reports
*  Automatic upload from CI to bintray -> master branch
    1. need to modify travis.yml: test assemble install bintrayUpload
    2. only in master branch
    3. auto increment version number
*  Add a concurrency Looper
*  Check Javadoc.
*  Migrate to >= Dagger2.11 (AndroidInjector)
*  Clean gradle files. use multiple gradle files.
*  warning javadoc.

License
-------

    MIT License

    Copyright (c) 2017 Alberto Penas Amor

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
