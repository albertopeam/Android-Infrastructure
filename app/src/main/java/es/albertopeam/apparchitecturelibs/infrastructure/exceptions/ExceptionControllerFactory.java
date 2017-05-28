package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import java.util.List;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

public class ExceptionControllerFactory {

    public static ExceptionController provide(List<ExceptionDelegate> delegates){
        return new ExceptionControllerImpl(delegates);
    }
}
