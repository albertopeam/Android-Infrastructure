package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

/**
 * Created by Al on 28/05/2017.
 */

public interface ExceptionDelegate {
    boolean canHandle(Exception exception);
    Error handle(Exception exception);
}
