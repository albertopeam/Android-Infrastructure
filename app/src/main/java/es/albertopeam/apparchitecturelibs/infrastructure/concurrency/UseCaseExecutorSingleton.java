package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import java.util.ArrayList;

import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionControllerFactory;

import static es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegateFactory.provide;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */

public class UseCaseExecutorSingleton {


    private static UseCaseExecutorImpl useCaseExecutorImpl;
    private static ExecutorImpl executor;
    private static MainThreadImpl mainThread;
    private static ExceptionController exceptionController;
    private static Tasks tasks;


    /**
     * Provides the use case executor with defults
     * @return UseCaseExecutor
     */
    public static UseCaseExecutor instance(){
        if (useCaseExecutorImpl == null){
            useCaseExecutorImpl = new UseCaseExecutorImpl(
                    executor = new ExecutorImpl(),
                    mainThread = new MainThreadImpl(),
                    exceptionController = ExceptionControllerFactory.provide(provide()),
                    tasks = new Tasks(new ArrayList<Task>())
            );
        }
        return useCaseExecutorImpl;
    }


    /**
     * Provides the use case executor
     * @param aExceptionController custom exception controller
     * @return UseCaseExecutor
     */
    public static UseCaseExecutor instance(ExceptionController aExceptionController){
        exceptionController = aExceptionController;
        if (hasInstance()){
            useCaseExecutorImpl.setExceptionController(exceptionController);
        }else {
            useCaseExecutorImpl = new UseCaseExecutorImpl(
                    executor = new ExecutorImpl(),
                    mainThread = new MainThreadImpl(),
                    exceptionController = aExceptionController,
                    tasks = new Tasks(new ArrayList<Task>())
            );
        }
        return useCaseExecutorImpl;
    }


    private static boolean hasInstance(){
        return useCaseExecutorImpl != null;
    }


    public static void release(){
        if (useCaseExecutorImpl != null){
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
