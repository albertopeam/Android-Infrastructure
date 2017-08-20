package com.github.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

import com.github.albertopeam.infrastructure.exceptions.HandledException;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 *
 * Generic callback used to respond to the caller of an {@link UseCase}
 */
public interface Callback<Response> {
    /**
     * Invoked when a {@link UseCase} is finallized normally
     * @param response data
     */
    void onSuccess(@NonNull Response response);

    /**
     * Invoked when an {@link UseCase} generates an exception during its execution
     * @param handledException representing the exception captured. Check {@link HandledException} to see its options for
     *              recover or continue
     */
    void onException(@NonNull HandledException handledException);
}
