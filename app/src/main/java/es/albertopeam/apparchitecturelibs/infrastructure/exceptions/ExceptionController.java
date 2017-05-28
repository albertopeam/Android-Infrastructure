package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

public interface ExceptionController {
    Error handle(Exception e);
}
