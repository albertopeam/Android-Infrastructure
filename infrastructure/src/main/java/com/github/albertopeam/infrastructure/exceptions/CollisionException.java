package com.github.albertopeam.infrastructure.exceptions;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Al on 20/08/2017.
 *
 * This exception represents a collision of delegates when handling an Exception. There must only
 * be one delegate that handles an Exception for a concrete scope.
 */

class CollisionException
        extends RuntimeException {

    private final List<ExceptionDelegate>delegates;

    CollisionException(@NonNull Exception exception,
                       @NonNull List<ExceptionDelegate> delegates){
        super(exception);
        this.delegates = delegates;
    }

    @Override
    public String getMessage() {
        return "There are more than one delegate that handles the same exception: "
                + getCause().getMessage() +
                ", delegates: " + fullTrace();
    }

    String fullTrace(){
        StringBuilder builder = new StringBuilder();
        for (ExceptionDelegate delegate:delegates){
            builder.append(delegate.getClass().getName());
            if (delegates.indexOf(delegate) < delegates.size() - 1){
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}
