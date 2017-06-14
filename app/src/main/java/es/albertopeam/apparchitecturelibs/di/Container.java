package es.albertopeam.apparchitecturelibs.di;

import es.albertopeam.apparchitecturelibs.App;

/**
 * Created by Al on 15/06/2017.
 */

public class Container {


    private AppComponent appComponent;


    public Container(App app) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppComponent.Module(app))
                .build();
    }

}
