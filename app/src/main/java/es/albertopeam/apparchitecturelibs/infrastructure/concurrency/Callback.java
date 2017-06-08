package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.Error;

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
    void onSuccess(Response response);

    /**
     * Invoked when an {@link UseCase} generates an exception during its execution
     * @param error representing the exception captured. Check {@link Error} to see its options for
     *              recover or continue
     */
    void onError(Error error);
}
