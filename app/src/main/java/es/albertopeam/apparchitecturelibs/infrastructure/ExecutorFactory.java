package es.albertopeam.apparchitecturelibs.infrastructure;

import java.util.concurrent.Executor;

/**
 * Created by Al on 25/05/2017.
 */

public class ExecutorFactory {


    private static Executor executor;


    public static Executor provide(){
        if (executor == null){
            executor = new ExecutorImpl();
        }
        return executor;
    }
}
