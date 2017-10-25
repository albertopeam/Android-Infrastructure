package com.github.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collections;
import java.util.concurrent.Executor;

import com.github.albertopeam.infrastructure.exceptions.HandledException;
import com.github.albertopeam.infrastructure.exceptions.ExceptionController;


/**
 * Created by Alberto Penas Amor on 25/05/2017.
 *
 * Concrete implementation of UseCaseExecutor
 */

class UseCaseExecutorImpl
    implements UseCaseExecutor{


    private Executor executor;
    private AndroidMainThread mainThread;
    private Tasks tasks;


    UseCaseExecutorImpl(@NonNull Executor executor,
                        @NonNull AndroidMainThread mainThread,
                        @NonNull Tasks tasks) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.tasks = tasks;
    }


    @Override
    public <Args, Response> boolean execute(final Args args,
                                           final @NonNull UseCase<Args, Response> useCase,
                                           final @NonNull Callback<Response>callback){
        if (tasks.alreadyAdded(useCase)){
            return false;
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (useCase.canRun()){
                        tasks.addUseCase(useCase);
                        Response response = useCase.run(args);
                        notifySuccess(useCase, callback, response);
                        tasks.removeUseCase(useCase);
                    }
                } catch (Exception e) {
                    ExceptionController exceptionController = useCase.exceptionController();
                    final HandledException handledException = exceptionController.handle(e);
                    notifyError(useCase, callback, handledException);
                    tasks.removeUseCase(useCase);
                }

            }
        });
        return true;
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
            }
        });
    }


    private void notifyError(final @NonNull UseCase useCase,
                             final @NonNull Callback callback,
                             final @NonNull HandledException handledException){
        mainThread.execute(new Runnable() {
            @Override
            public void run() {
                if (useCase.canRespond()){
                    callback.onException(handledException);
                }
            }
        });
    }
}
