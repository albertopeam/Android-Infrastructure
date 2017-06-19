package es.albertopeam.apparchitecturelibs.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import es.albertopeam.apparchitecturelibs.notes.NotesActivitySubcomponent;

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {
    void inject(Application androidApp);
    NotesActivitySubcomponent plus(NotesActivitySubcomponent.NotesModule module);
    //HOW TO:
    //IMPORTANT: EXPOSE HERE ALL OBJECTS THAT ARE GOING TO BE SHARED THROUGH DIFERENT SCOPES!!!
    //IMPORTANT: REMEMBER TO NOT EXPOSE ANY IN 'SUBCOMPONENTS'
}