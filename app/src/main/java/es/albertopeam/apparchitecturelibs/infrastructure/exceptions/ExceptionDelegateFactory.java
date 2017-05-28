package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Al on 28/05/2017.
 */

public class ExceptionDelegateFactory {

    private static List<ExceptionDelegate>delegates = new ArrayList<>();

    static {
        delegates.add(new NPExceptionDelegate());
    }

    public static List<ExceptionDelegate> provide(){
        return delegates;
    }
}
