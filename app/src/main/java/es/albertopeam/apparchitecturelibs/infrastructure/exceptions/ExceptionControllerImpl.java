package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import java.util.List;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

class ExceptionControllerImpl
        implements ExceptionController{


    private List<ExceptionDelegate> delegates;


    ExceptionControllerImpl(List<ExceptionDelegate> delegates) {
        this.delegates = delegates;
    }


    @Override
    public Error handle(Exception exception) {
        for (ExceptionDelegate delegate:delegates){
            if (delegate.canHandle(exception)){
                return delegate.handle(exception);
            }
        }
        return new NotHandledError();
    }
}
