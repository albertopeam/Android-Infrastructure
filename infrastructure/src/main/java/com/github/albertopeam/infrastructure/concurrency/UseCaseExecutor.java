package com.github.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 *
 * Used for send to background execution a use case {@link UseCase} instance
 */
public interface UseCaseExecutor {
    /**
     * Invoke this method to execute {@link UseCase} async, the {@link UseCase} is not going to be
     * executed if the {@link LifecycleState} is not one of: CREATED || STARTED || RESUMED.
     * @param args passed to {@link UseCase} for its exection
     * @param useCase to execute in background thread
     * @param successCallback to respond in main thread after the execution of the {@link UseCase}
     * @return true if added to the execution queue, false if already executing.
     */
    <Args, Response> boolean execute(final Args args,
                                     final @NonNull UseCase<Args, Response> useCase,
                                     final @NonNull SuccessCallback<Response> successCallback,
                                     final @NonNull ExceptionCallback exceptionCallback);
}
