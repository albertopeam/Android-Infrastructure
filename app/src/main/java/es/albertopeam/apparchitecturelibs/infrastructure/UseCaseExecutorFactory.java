package es.albertopeam.apparchitecturelibs.infrastructure;

import java.util.concurrent.Executor;

/**
 * Created by Al on 25/05/2017.
 */

public class UseCaseExecutorFactory {


    private static UseCaseExecutorImpl useCaseExecutorImpl;
    private static ExecutorImpl executor;
    private static MainThreadImpl mainThread;
    private static ErrorFactory errorFactory;


    public static UseCaseExecutor provide(){
        if (useCaseExecutorImpl == null){
            useCaseExecutorImpl = new UseCaseExecutorImpl(
                    executor = new ExecutorImpl(),
                    mainThread = new MainThreadImpl(),
                    errorFactory = new ErrorFactory()
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
        if (errorFactory != null){
            errorFactory = null;
        }
    }
}
