package es.albertopeam.apparchitecturelibs.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.albertopeam.apparchitecturelibs.App;
import es.albertopeam.infrastructure.concurrency.UseCaseExecutor;
import es.albertopeam.infrastructure.concurrency.UseCaseExecutorFactory;
import es.albertopeam.infrastructure.exceptions.ExceptionController;
import es.albertopeam.infrastructure.exceptions.ExceptionControllerFactory;
import es.albertopeam.infrastructure.exceptions.ExceptionDelegate;

@Module
public class AppModule {


    private App app;


    public AppModule(App app) {
        this.app = app;
    }


    @Provides
    @Singleton
    public UseCaseExecutor provideUseCaseExecutor(ExceptionController exceptionController){
        return UseCaseExecutorFactory.provide(exceptionController);
    }


    @Provides
    @Singleton
    public ExceptionController provideExceptionController(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        return ExceptionControllerFactory.provide(delegates);
    }
}