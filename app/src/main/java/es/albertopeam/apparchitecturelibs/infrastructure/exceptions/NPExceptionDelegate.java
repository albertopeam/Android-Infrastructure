package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

/**
 * Created by Al on 28/05/2017.
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
}
