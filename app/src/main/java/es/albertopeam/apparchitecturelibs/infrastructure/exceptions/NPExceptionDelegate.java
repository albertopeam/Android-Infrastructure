package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import android.arch.lifecycle.LifecycleOwner;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

class NPExceptionDelegate
    implements ExceptionDelegate{


    @Override
    public boolean canHandle(Exception exception) {
        return exception instanceof NullPointerException;
    }

    @Override
    public Error handle(Exception exception) {
        return new NotRecoverableError(exception.getMessage());
    }

    @Override
    public boolean belongsTo(LifecycleOwner lifecycleOwner) {
        return false;
    }
}
