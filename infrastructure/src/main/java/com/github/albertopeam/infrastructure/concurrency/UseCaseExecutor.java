package com.github.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 *
 * Used for send to background execution a use case {@link UseCase} instance
 */
public interface UseCaseExecutor {
    /**
     * Invoke this method to execute {@link UseCase} async, the {@link UseCase} is going to be
     * executed if the {@link LifecycleState} is INITIALIZED and NOT DESTROYED.
     * @param args passed to {@link UseCase} for its execution
     * @param useCase to execute in background thread
     * @param successCallback to respond a successful response in main thread after the execution of the {@link UseCase}
     * @param exceptionCallback to respond that an {@link Exception } happens during the execution of the {@link UseCase}
     * @param <Args> generic input
     * @param <Response> generic response
     * @return true if added to the execution queue, false if already executing.
     */
    <Args, Response> boolean execute(final Args args,
                                     final @NonNull UseCase<Args, Response> useCase,
                                     final @NonNull SuccessCallback<Response> successCallback,
                                     final @NonNull ExceptionCallback exceptionCallback);
}
