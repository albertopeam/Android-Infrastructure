package es.albertopeam.apparchitecturelibs.infrastructure;

import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionControllerFactory;

import static es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegateFactory.provide;

/**
 * Created by Al on 25/05/2017.
 */

public class UseCaseExecutorSingleton {


    private static UseCaseExecutorImpl useCaseExecutorImpl;
    private static ExecutorImpl executor;
    private static MainThreadImpl mainThread;
    private static ExceptionController exceptionController;


    public static UseCaseExecutor instance(){
        if (useCaseExecutorImpl == null){
            useCaseExecutorImpl = new UseCaseExecutorImpl(
                    executor = new ExecutorImpl(),
                    mainThread = new MainThreadImpl(),
                    exceptionController = ExceptionControllerFactory.provide(provide())
            );
        }
        return useCaseExecutorImpl;
    }


    public static void release(){
        if (useCaseExecutorImpl != null){
            useCaseExecutorImpl.cancelAll();
            useCaseExecutorImpl = null;
        }
        if (executor != null){
            executor.shutdown();
            executor = null;
        }
        if (mainThread != null){
            mainThread = null;
        }
        if (exceptionController != null){
            exceptionController = null;
        }
    }
}
