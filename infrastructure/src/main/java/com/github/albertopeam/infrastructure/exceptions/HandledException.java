package com.github.albertopeam.infrastructure.exceptions;

import android.support.annotation.NonNull;

import com.github.albertopeam.infrastructure.concurrency.UseCase;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 *
 * Base class that represents an handled exception and the logic to recover from this.
 * This is created during the execution of an {@link UseCase} to declare that something was bad.
 * We can recover from the exception via {@link #recover()}.
 */
public abstract class HandledException extends Exception{

    public HandledException(@NonNull Exception exception) {
        super(exception);
    }

    /**
     * Recover from the Exception
     */
    public abstract void recover();
}
