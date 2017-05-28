package es.albertopeam.apparchitecturelibs;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import es.albertopeam.apparchitecturelibs.data.DatabaseFactory;
import es.albertopeam.apparchitecturelibs.data.DatabaseSingleton;

public class App
        extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
        initDatabase();
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