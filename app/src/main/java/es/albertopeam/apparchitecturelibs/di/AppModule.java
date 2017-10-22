package es.albertopeam.apparchitecturelibs.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.albertopeam.apparchitecturelibs.App;
import com.github.albertopeam.infrastructure.concurrency.UseCaseExecutor;
import com.github.albertopeam.infrastructure.concurrency.UseCaseExecutorFactory;
import com.github.albertopeam.infrastructure.exceptions.ExceptionController;
import com.github.albertopeam.infrastructure.exceptions.ExceptionControllerFactory;
import com.github.albertopeam.infrastructure.exceptions.ExceptionDelegate;

@Module
public class AppModule {


    private App app;


    public AppModule(App app) {
        this.app = app;
    }


    @Provides
    @Singleton
    public UseCaseExecutor provideUseCaseExecutor(){
        return UseCaseExecutorFactory.provide();
    }
}