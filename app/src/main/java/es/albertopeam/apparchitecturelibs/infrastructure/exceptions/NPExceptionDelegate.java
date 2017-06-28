package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import android.arch.lifecycle.LifecycleOwner;

import es.albertopeam.apparchitecturelibs.R;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 28/05/2017.
 *
 * Represents an {@link ExceptionDelegate} that handles NullPointerException
 */

class NPExceptionDelegate
    implements ExceptionDelegate{


    @Override
    public boolean canHandle(Exception exception) {
        return exception instanceof NullPointerException;
    }

    @Override
    public Error handle(Exception exception) {
        return new NotRecoverableError(R.string.null_pointer_exception);
    }

    @Override
    public boolean belongsTo(LifecycleOwner lifecycleOwner) {
        return false;
    }
}
