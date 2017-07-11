### Android Infrastructure

[ ![Download](https://api.bintray.com/packages/albertopeam/android/Concurrency/images/download.svg) ](https://bintray.com/albertopeam/android/Concurrency/_latestVersion)
[![Build Status](https://travis-ci.org/albertopeam/Android-Infrastructure.svg?branch=master)](https://travis-ci.org/albertopeam/Android-Infrastructure)
[![codecov](https://codecov.io/gh/albertopeam/Android-Infrastructure/branch/master/graph/badge.svg)](https://codecov.io/gh/albertopeam/Android-Infrastructure)
[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/albertopeam/Android-Infrastructure/blob/master/LICENSE)

The library intention /infrastructure is to help developers to handle
multithreading andexception catching in a clean way, respecting the
SOLID principles.
One of the main goals is to allow the "created" threads and how to
communicate between the Android main thread and the background threads.
Another goal is to define a common way to handle the exceptions thrown
during the execution of our services(by services we understand any
operation that must be encapsulated into an object and decouple outside
of the view code).

The exampe app/ shows how to build and app using mvp with the aim of
this library. It provides the basic configuration to create an use case
executor and an exception controller, it shows how to create three
sync services and wrap it on async use cases, the power is that we
can build many sync services and compose onto a async use case.
The project use Dagger2 DI framework, and one android alpha library(
in a few months with the release of Android O will leave alpha/beta).
The alpha dependency:
```
"android.arch.lifecycle:runtime:1.0.0-alpha3"
```
The project is a very basic app to list, create and destroy notes. The
notes removal is not implemented intentionally to show the usage of
the scoped exception delegates.

### Install
[Gradle Dependency](#dependency)

### Usage
1. [Create exception handler](#createexceptionhandler)
2. [Create use case executor](#createinfra)
3. [Create an use case](#createusecase)
4. [Connect all to the presenter](#connectopresenter)
5. [Scoped delegate](#scopeddelegate)

### Testing
[Test the activity](#testandroidui)


Install
-------

##### <a name="dependency">Gradle Dependency</a>

The Gradle dependency is available via jCenter.

The minimum API level supported by this library is API 15 (ICE_CREAM_SANDWICH_MR1).
```groovy
compile 'com.github.albertopeam:infrastructure:0.0.1-alpha'
```

Usage
-----

##### <a name="createexceptionhandler">1. Create exception handler</a>
ExceptionController class handles all exceptions that are thrown during
the use case execution. This will need a list of delegates as parameter,
every one will handle a concrete expception.

The next example covers a exception delegate that handle only a
exception, a NullPointerException, it will return an Error that contains
a description of the exception.
```java
ExceptionDelegate aDelegate = new ExceptionDelegate() {
    @Override
    public boolean canHandle(Exception exception) {
        return exception instanceof NullPointerException;
    }

    @Override
    public Error handle(Exception exception) {
        return new Error() {
            @Override
            public boolean isRecoverable() {
                return false;
            }

            @Override
            public String message() {
                return "Oh, there is an internal error.";
            }

            @Override
            public void recover() {
                //not recoverable
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
ExceptionController exceptController = ExceptionControllerFactory.provide(delegates);
```

##### <a name="createinfra">2. Create use case executor</a>
This use case executor provides the ability to run code in background and
when it completes invoke the Android main thread with the result.
This executor need as a parameter an implementation of a
ExceptionController, this have been created in step one,
[Create exception handler](#createexceptionhandler).
```java
UseCaseExecutor useCaseExecutor = UseCaseExecutorFactory.provide(exceptionController);
```

##### <a name="createusecase">3. Create an use case</a>
An use case is a piece of code that executes an operation and returns a
result. This operation will be executed in a background thread. The result
will be posted to main thread.
We will need to pass as parameter a lifecycle, this lifecycle will
handle use case cancelation in the case that the android component be
destroyed. This lifecycle is in alpha, to get more information visit:
[Android lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle.html)

In this example we will only return the string converted to uppercase.
```java
Lifecycle lifecycle = activity.getLifecycle();
UseCase<String, String> upperCaseUseCase = new UseCase<String, String>(lifecycle){

    @Override
    protected String run(String s) throws Exception {
        return s.toUpperCase();
    }
};
```

##### <a name="connectopresenter">4 Connect all to the presenter</a>
The presenter will handle the view(activity) input events and the
use case executor output events. We will need to inject all the dependencies
created below.
If any exception is triggered in the use case there is a block to check if the Error
can be recovered or not.
In case of success the callback will invoke the onUpperCase method to
send the result to the activity.
```java
void toUpperCase(String aString){
    useCaseExecutor.execute(aString, upperCaseUseCase, new Callback<String>(){
                @Override
                public void onSuccess(String note) {
                    view.onUpperCase(note);
                }

                @Override
                public void onError(Error error) {
                    if (error.isRecoverable()){
                        error.recover();
                    }else {
                        view.showError(error.message());
                    }
                }
            });
}

```
Activity code that handles events from/toward the presenter.
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

    void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

}
```

##### <a name="scopeddelegate">5 Scoped delegate</a>
Scoped delegates can handle exceptions that are only triggered in a concrete
scenario. For example an activity where we dont have available an Android Api,
in this case we can add a scoped delegate that will only be used during
the activity Lifecycle.

Scoped delegates have a method that must return if the current scope
belongs or not to the Lyfecicle passed as parameter.
```
public boolean belongsTo(LifecycleOwner lifecycleOwner);
```

This example shows how it can be used. This delegate must be added to the
ExceptionController, when the scope of the Android component be destroyed
then ExceptionDelegate will be removed from the list
of delegates that the ExceptionController has.

```java
class UnsupportedOperationExceptionDelegate
        implements ExceptionDelegate {

    private WeakReference<Activity> activityWeakReference;


    UnsupportedOperationExceptionDelegate(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }


    @Override
    public boolean canHandle(Exception exception) {
        return exception instanceof UnsupportedOperationException;
    }

    @Override
    public Error handle(Exception exception) {
        return new Error() {
            @Override
            public boolean isRecoverable() {
                return true;
            }

            @Override
            public String message() {
                return "Unsupported operation";
            }

            @Override
            public void recover() {
                new MaterialDialog.Builder(activityWeakReference.get())
                        .content(message())
                        .positiveText("ok")
                        .show();
            }
        };
    }

    @Override
    public boolean belongsTo(LifecycleOwner lifecycleOwner) {
        return activityWeakReference.get() == null || lifecycleOwner == activityWeakReference.get();
    }
}
```
Create an instance of this class and add it to the ExceptionController

```java
ExceptionDelegate delegate = new UnsupportedOperationExceptionDelegate(activity);
exceptionController.addDelegate(delegate, notesActivity.getLifecycle());
```


Testing
-------

##### <a name="testandroidui">Test the activity</a>
To test all this stuff that is mounted with the help of Dagger 2 we can
use a library called [DaggerMock](https://github.com/fabioCollini/DaggerMock) .
This library overrides the objects provided by the dagger modules, that
way we can replace dependencies with test doubles.

First of all we need to replace the first Component that is created in the
graph. We are assuming that the main component is called AppComponent/AppModule,
the Application class name is App.

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
Finally create the EspressoDaggerMockRule. With the setup complete we can
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

Todos:
------
*  Clean gradle files. use multiple gradle files.
*  androidTest or only test?
*  review fb build library
*  Rename domain usecases to services
*  warning javadoc.
*  Check Javadoc. Review: concurrency and exceptions.
*  Automatic upload from CI to bintray.(auto build number)
*  Add a concurrency Looper, already done in old project.
*  Maybe break the interface of ExceptionController in two: use/build
*  Library wiki(Github) with more samples: Services.
*  Migrate to Dagger2 AndroidInjector.


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
