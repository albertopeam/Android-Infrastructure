package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import java.util.List;

/**
 * Created by Al on 28/05/2017.
 */

public class ExceptionController {


    private List<ExceptionDelegate> delegates;


    public ExceptionController(List<ExceptionDelegate> delegates) {
        this.delegates = delegates;
    }


    public Error handle(Exception exception) throws NotHandledException {
        for (ExceptionDelegate delegate:delegates){
            if (delegate.canHandle(exception)){
                return delegate.handle(exception);
            }
        }
        throw new NotHandledException("This exception is not handled: " + exception.getMessage());
    }
}
