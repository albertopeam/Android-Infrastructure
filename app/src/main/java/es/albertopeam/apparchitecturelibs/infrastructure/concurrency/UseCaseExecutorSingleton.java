package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import java.util.ArrayList;

import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionControllerFactory;

import static es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegateFactory.provide;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 25/05/2017.
 *
 * This class is a singleton and represents a way of execute async code and handle errors.
 * It provides an {@link UseCaseExecutor} to execute {@link UseCase} intances. Also it contains
 * an {@link ExceptionController} that handles the exceptions raised during the execution of the
 * {@link UseCase} instances.
 */
public class UseCaseExecutorSingleton {


    private static UseCaseExecutorImpl useCaseExecutorImpl;
    private static ExecutorImpl executor;
    private static AndroidMainThreadImpl mainThread;
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
                    mainThread = new AndroidMainThreadImpl(),
                    exceptionController = ExceptionControllerFactory.provide(provide()),
                    tasks = new Tasks(new ArrayList<Task>())
            );
        }
        return useCaseExecutorImpl;
    }


    /**
     * Provides the use case executor
     * @param aExceptionController custom {@link ExceptionController}
     * @return UseCaseExecutor
     */
    public static UseCaseExecutor instance(ExceptionController aExceptionController){
        exceptionController = aExceptionController;
        if (hasInstance()){
            useCaseExecutorImpl.setExceptionController(exceptionController);
        }else {
            useCaseExecutorImpl = new UseCaseExecutorImpl(
                    executor = new ExecutorImpl(),
                    mainThread = new AndroidMainThreadImpl(),
                    exceptionController = aExceptionController,
                    tasks = new Tasks(new ArrayList<Task>())
            );
        }
        return useCaseExecutorImpl;
    }


    private static boolean hasInstance(){
        return useCaseExecutorImpl != null;
    }


    /**
     * Release the pool of threads that handle this class
     */
    public static void release(){
        if (executor != null) {
            executor.shutdown();
        }
        useCaseExecutorImpl = null;
        executor = null;
        mainThread = null;
        exceptionController = null;
        tasks = null;
    }
}
