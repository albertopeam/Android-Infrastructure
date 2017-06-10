package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import java.util.concurrent.Executor;

import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.Error;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */

class UseCaseExecutorImpl
    implements UseCaseExecutor{


    private ExceptionController exceptionController;
    private Executor executor;
    private AndroidMainThread mainThread;
    private Tasks tasks;


    UseCaseExecutorImpl(Executor executor,
                        AndroidMainThread mainThread,
                        ExceptionController exceptionController,
                        Tasks tasks) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.exceptionController = exceptionController;
        this.tasks = tasks;
    }


    @Override
    public <Args, Response> boolean execute(final Args args,
                                           final UseCase<Args, Response> useCase,
                                           final Callback<Response>callback){
        if (tasks.alreadyAdded(useCase)){
            return false;
        }
        tasks.addUseCase(useCase);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = useCase.run(args);
                    notifySuccess(useCase, callback, response);
                } catch (Exception e) {
                    notifyError(useCase, callback, e);
                }

            }
        });
        return true;
    }


    synchronized void setExceptionController(ExceptionController exceptionController) {
        this.exceptionController = exceptionController;
    }


    private <Args, Response> void notifySuccess(
                                final UseCase<Args, Response> useCase,
                                final Callback<Response> callback,
                                final Response success){
        mainThread.execute(new Runnable() {
            @Override
            public void run() {
                if (useCase.canRespond()){
                    callback.onSuccess(success);
                }
                tasks.removeUseCase(useCase);
            }
        });
    }


    private void notifyError(final UseCase useCase,
                             final Callback callback,
                             final Exception e){
        mainThread.execute(new Runnable() {
            @Override
            public void run() {
                if (useCase.canRespond()){
                    final Error error = exceptionController.handle(e);
                    callback.onError(error);
                }
                tasks.removeUseCase(useCase);
            }
        });
    }
}
