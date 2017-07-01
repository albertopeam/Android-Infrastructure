package es.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import es.albertopeam.infrastructure.exceptions.Error;
import es.albertopeam.infrastructure.exceptions.ExceptionController;


/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */

class UseCaseExecutorImpl
    implements UseCaseExecutor{


    private ExceptionController exceptionController;
    private Executor executor;
    private AndroidMainThread mainThread;
    private Tasks tasks;


    UseCaseExecutorImpl(@NonNull Executor executor,
                        @NonNull AndroidMainThread mainThread,
                        @NonNull ExceptionController exceptionController,
                        @NonNull Tasks tasks) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.exceptionController = exceptionController;
        this.tasks = tasks;
    }


    @Override
    public <Args, Response> boolean execute(final Args args,
                                           final @NonNull UseCase<Args, Response> useCase,
                                           final @NonNull Callback<Response>callback){
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


    synchronized void setExceptionController(@NonNull ExceptionController exceptionController) {
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


    private void notifyError(final @NonNull UseCase useCase,
                             final @NonNull Callback callback,
                             final @NonNull Exception e){
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
