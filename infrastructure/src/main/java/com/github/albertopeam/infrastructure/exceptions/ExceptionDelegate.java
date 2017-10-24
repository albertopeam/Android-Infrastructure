package com.github.albertopeam.infrastructure.exceptions;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 *
 * This interface provides a way of handle a {@link Exception}.
 */

public interface ExceptionDelegate {
    /**
     * Check if this delegate can handle a Exception
     * @param exception that is checked
     * @return true if handled, false otherwise
     */
    boolean canHandle(@NonNull Exception exception);

    /**
     * Handle the Exception
     * @param exception to be handled
     * @return an HandledException that represents an Exception which may or not be recovered
     */
    HandledException handle(@NonNull Exception exception);
}
