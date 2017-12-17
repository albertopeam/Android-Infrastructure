package com.github.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

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
                                            final @NonNull SuccessCallback<Response> successCallback,
                                            final @NonNull ExceptionCallback exceptionCallback){
        if (tasks.alreadyAdded(useCase)){
            return false;
        }
        executor.execute(() -> {
            try {
                if (useCase.canRun()){
                    tasks.addUseCase(useCase);
                    Response response = useCase.run(args);
                    notifySuccess(useCase, successCallback, response);
                    tasks.removeUseCase(useCase);
                }
            } catch (Exception e) {
                ExceptionController exceptionController = useCase.exceptionController();
                final HandledException handledException = exceptionController.handle(e);
                notifyError(useCase, exceptionCallback, handledException);
                tasks.removeUseCase(useCase);
            }

        });
        return true;
    }


    private <Args, Response> void notifySuccess(
                                final UseCase<Args, Response> useCase,
                                final SuccessCallback<Response> successCallback,
                                final Response success){
        mainThread.execute(() -> {
            if (useCase.canRespond()){
                successCallback.onSuccess(success);
            }
        });
    }


    private void notifyError(final @NonNull UseCase useCase,
                             final @NonNull ExceptionCallback exceptionCallback,
                             final @NonNull HandledException handledException){
        mainThread.execute(() -> {
            if (useCase.canRespond()){
                exceptionCallback.onException(handledException);
            }
        });
    }
}
