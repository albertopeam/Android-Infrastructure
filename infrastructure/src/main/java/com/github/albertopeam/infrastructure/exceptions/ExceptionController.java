package com.github.albertopeam.infrastructure.exceptions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.albertopeam.infrastructure.concurrency.UseCase;


/**
 * Created by Alberto Penas Amor on 28/05/2017.
 *
 * This interface provides a way of handle Exceptions raised during the execution of
 * {@link UseCase}, it returns an
 * {@link HandledException} that represents how we can recover from the handled Exception.
 *
 * It can be added scoped {@link ExceptionDelegate} that will only live during the scope of its
 * {@link Lifecycle}. This delegates will be removed automatically when its Lifecycle be destroyed.
 */

public interface ExceptionController {
    /**
     * Handles an Exception.
     * @param exception to be handled
     * @param lifecycleOwner scope where the exception is thrown
     * @return an HandledException representing how we can recover from the handled Exception
     */
    HandledException handle(@NonNull Exception exception, @Nullable LifecycleOwner lifecycleOwner);

    /**
     * Adds a scoped {@link ExceptionDelegate} to this ExceptionController
     * @param delegate to handle exception
     * @param lifecycle to which the delegate will be attached
     */
    void addDelegate(@NonNull ExceptionDelegate delegate, @NonNull Lifecycle lifecycle);
}
