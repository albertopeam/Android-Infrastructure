package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import android.arch.lifecycle.Lifecycle;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 *
 * This interface provides a way of handle Exceptions raised during the execution of
 * {@link es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCase}, it returns an
 * {@link Error} that represents the way in which we are going to handle the captured Exception.
 *
 * It can be added scoped {@link ExceptionDelegate} that will only live during the scope of its
 * {@link Lifecycle}. This delegates will be removed automatically when its Lifecicle be destroyed.
 */

public interface ExceptionController {
    /**
     * Handles an Exception.
     * @param exception to be handled
     * @return an Error representing how we are going to handle the Exception
     */
    Error handle(Exception exception);

    /**
     * Adds a scoped {@link ExceptionDelegate} to this ExceptionController
     * @param delegate to handle exception
     * @param lifecycle to which the delegate will be attached
     */
    void addDelegate(ExceptionDelegate delegate, Lifecycle lifecycle);
}
