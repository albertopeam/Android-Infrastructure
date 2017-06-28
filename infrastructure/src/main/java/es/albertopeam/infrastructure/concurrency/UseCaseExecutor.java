package es.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 25/05/2017.
 *
 * Used for send to background execution a use case {@link UseCase} instance
 */
public interface UseCaseExecutor {
    /**
     * Invoke this method to execute {@link UseCase} async
     * @param args passed to {@link UseCase} for its exection
     * @param useCase to execute in background thread
     * @param callback to respond in main thread after the execution of the {@link UseCase}
     * @return true if sended to execution, false if already running
     */
    <Args, Response> boolean execute(final Args args,
                                     final @NonNull UseCase<Args, Response> useCase,
                                     final @NonNull Callback<Response> callback);
}
