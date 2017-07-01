package es.albertopeam.infrastructure.exceptions;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

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
     * @return an Error that represents an Exception which may or not be recovered
     */
    Error handle(@NonNull Exception exception);

    /**
     * Check if this delegate is inside the scope of the {@link LifecycleOwner}
     * @param lifecycleOwner to be checked
     * @return true if the scope of this delegate belongs to the {@link LifecycleOwner}, otherwise
     * false
     */
    boolean belongsTo(@NonNull LifecycleOwner lifecycleOwner);
}
