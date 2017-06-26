package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

public class ExceptionDelegateFactory {


    private static List<ExceptionDelegate>delegates = new ArrayList<>();


    static {
        delegates.add(new NPExceptionDelegate());
    }


    /**
     * Provides a copy of the default delegates
     * @return a inmutable collection of exception delegates
     */
    public static List<ExceptionDelegate> provide(){
        return delegates;
    }
}
