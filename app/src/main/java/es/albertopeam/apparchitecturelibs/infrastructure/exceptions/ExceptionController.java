package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

/**
 * Created by Al on 28/05/2017.
 */

public interface ExceptionController {
    Error handle(Exception e);
}
