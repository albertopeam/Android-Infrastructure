package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
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
    private MainThreadImpl mainThread;
    private Tasks tasks;


    UseCaseExecutorImpl(Executor executor,
                        MainThreadImpl mainThread,
                        ExceptionController exceptionController,
                        Tasks tasks) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.exceptionController = exceptionController;
        this.tasks = tasks;
    }


    @Override
    public <Args, Response> void execute(final Args args,
                                           final UseCase<Args, Response> useCase,
                                           final Lifecycle lifecycle,
                                           final Callback<Response>callback){
        if (tasks.alreadyAdded(useCase)){
            return;//TODO: maybe notify that already running...
        }
        tasks.addUseCase(useCase, lifecycle);
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
    }


    @Deprecated
    @Override
    public synchronized void cancel(UseCase... useCases){
        for (UseCase useCase:useCases) {
            try {
                Task task = tasks.findTask(useCase);
                task.canceled = true;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Deprecated
    synchronized void cancelAll(){
        for (Task task:tasks.tasks()) {
            try {
                task.canceled = true;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    synchronized void setExceptionController(ExceptionController exceptionController) {
        this.exceptionController = exceptionController;
    }


    private <Args, Response> void notifySuccess(
                                final UseCase<Args, Response> useCase,
                                final Callback<Response> callback,
                                final Response success){
        mainThread.run(new Runnable() {
            @Override
            public void run() {
                if (tasks.canRespond(useCase)){
                    callback.onSuccess(success);
                }
                tasks.removeUseCase(useCase);
            }
        });
    }


    private void notifyError(final UseCase useCase,
                             final Callback callback,
                             final Exception e){
        mainThread.run(new Runnable() {
            @Override
            public void run() {
                if (tasks.canRespond(useCase)){
                    final Error error = exceptionController.handle(e);
                    callback.onError(error);
                    tasks.removeUseCase(useCase);
                }
            }
        });
    }
}
