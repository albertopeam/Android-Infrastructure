package com.github.albertopeam.infrastructure.exceptions;

import android.support.annotation.NonNull;

/**
 * Created by Al on 20/08/2017.
 *
 * This exception represents a programing error that happens when the list of delegates not handles an Exception.
 * There must be at least one delegate that handles each one of the Exceptions triggered for a concrete scope.
 */

class NotHandledException
        extends RuntimeException {

    NotHandledException(@NonNull Exception exception){
        super("There isn't any delegate that handles the Exception: " + exception.getMessage());
    }
}
