package es.albertopeam.apparchitecturelibs.di;

import es.albertopeam.apparchitecturelibs.App;
import es.albertopeam.apparchitecturelibs.notes.NotesActivity;
import es.albertopeam.apparchitecturelibs.notes.NotesActivitySubcomponent;

/**
 * Created by Al on 15/06/2017.
 */

public class Container {


    private AppComponent appComponent;


    public Container(App app) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .build();
    }


    public void inject(NotesActivity notesActivity){
        appComponent
                .plus(new NotesActivitySubcomponent.NotesModule(notesActivity))
                .inject(notesActivity);
    }

}
