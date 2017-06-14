package es.albertopeam.apparchitecturelibs;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import es.albertopeam.apparchitecturelibs.data.DatabaseSingleton;
import es.albertopeam.apparchitecturelibs.di.Container;

public class App
        extends Application {


    private Container container;

    @Override
    public void onCreate() {
        super.onCreate();
        initContainer();
        initLeakCanary();
        initDatabase();
    }


    private void initContainer() {
        container = new Container(this);
    }


    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {

        }else{
            LeakCanary.install(this);
        }
    }


    private void initDatabase(){
        DatabaseSingleton.init(this);
    }
}