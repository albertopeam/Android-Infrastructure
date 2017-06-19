package es.albertopeam.apparchitecturelibs.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.albertopeam.apparchitecturelibs.App;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutor;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutorFactory;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionControllerFactory;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegateFactory;

@Module
class AppModule {


    private App app;


    AppModule(App app) {
        this.app = app;
    }


    @Provides
    @Singleton
    UseCaseExecutor provideUseCaseExecutor(ExceptionController exceptionController){
        return UseCaseExecutorFactory.provide(exceptionController);
    }


    @Provides
    @Singleton
    ExceptionController provideExceptionController(){
        return ExceptionControllerFactory.provide(ExceptionDelegateFactory.provide());
    }
}