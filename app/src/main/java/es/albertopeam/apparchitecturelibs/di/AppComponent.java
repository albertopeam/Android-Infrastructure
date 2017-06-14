package es.albertopeam.apparchitecturelibs.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;
import es.albertopeam.apparchitecturelibs.App;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutor;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutorFactory;

@Singleton
@Component(modules={AppComponent.Module.class/*, ConcurrentModule.class, StorageModule.class*/})
public interface AppComponent {
    void inject(Application androidApp);

    //SplashActivityComponent plus(SplashActivityModule splashActivityModule);


    //HOW TO:
    //IMPORTANT: EXPOSE HERE ALL OBJECTS THAT ARE GOING TO BE SHARED THROUGH DIFERENT SCOPES!!!
    //IMPORTANT: REMEMBER TO NOT EXPOSE ANY IN 'SUBCOMPONENTS'
    //HOW TO:
    /*Application provideApplication();
    Retrofit provideRetrofit();
    Executor provideExecutor();
    AppExceptionMapper provideExceptionMapper();
    PersistentStorage provideStorage();
    */

    @dagger.Module
    class Module {


        private App app;


        Module(App app) {
            this.app = app;
        }


        @Provides
        @Singleton
        UseCaseExecutor provide(){
            return UseCaseExecutorFactory.provide();
        }
    }
}