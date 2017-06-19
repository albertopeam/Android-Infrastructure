package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import android.arch.lifecycle.LifecycleOwner;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

public interface ExceptionDelegate {
    boolean canHandle(Exception exception);
    Error handle(Exception exception);
    boolean belongsTo(LifecycleOwner lifecycleOwner);
}
