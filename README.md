### Android architecture
This project shows how to build and app using mvp. It provides
the basic infrastructure to handle multhithreading and errors.
The project use Dagger2 DI framework. It uses some alpha android
libraries.

The project is a sample that list, create and remove notes.

### Table of contents
1. [Create exception handler](#createexceptionhandler)
2. [Create use case executor](#createinfra)
3. [Create an use case](#createusecase)
4. [Connect all to the presenter](#connectopresenter)
5. [Scoped delegate](#scopeddelegate)
6. [Test the activity](#testandroidui)

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
We will need to pass as parameter a Lifecycle. This is in alpha, to get
more information visit:
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
Activity code that handles the received events from the presenter.
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
scenario. For example an activity where we need location permissions can
thrown an SecurityException, in this case we can add a scoped delegate
that will only be used during its activity Lifecycle.
```java
TODO: example
```

##### <a name="testandroidui">6 Test the activity</a>
```java
TODO: daggermock
```

#### Todos:
*  Replace NotRecoverableError String property with a int reference to support I18N
*  Test: unit and infrastructure(database)
*  Comment code: infrastructure->exceptions, review concurrency
*  Decouple infrastructure to a library and upload to bintray.
*  Library wiki(Github) with samples
*  Mount a jenkinsfile. Local jenkins. Search free jenkins providers.
*  Automatic upload from jenkins to bintray.

#### Collaborations
Via PR. They will be well received
