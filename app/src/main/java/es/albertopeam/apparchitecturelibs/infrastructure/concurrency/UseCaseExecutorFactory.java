package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import java.util.ArrayList;

import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionControllerFactory;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegateFactory;

import static es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegateFactory.provide;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 *
 * This class is a factory to provide an UseCaseExecutor, an object capable of execute async code
 * and handle errors.
 * It provides an {@link UseCaseExecutor} to execute {@link UseCase} intances. Also it contains
 * an {@link ExceptionController} that handles the exceptions raised during the execution of the
 * {@link UseCase} instances.
 */
public class UseCaseExecutorFactory {


    /**
     * Provides the use case executor with defults
     */
    public static UseCaseExecutor provide(){
        UseCaseExecutorImpl useCaseExecutorImpl = new UseCaseExecutorImpl(
                    new ExecutorImpl(),
                    new AndroidMainThreadImpl(),
                    ExceptionControllerFactory.provide(ExceptionDelegateFactory.provide()),
                    new Tasks(new ArrayList<Task>())
            );
        return useCaseExecutorImpl;
    }

}
