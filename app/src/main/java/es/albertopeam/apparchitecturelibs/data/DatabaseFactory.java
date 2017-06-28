package es.albertopeam.apparchitecturelibs.data;

import es.albertopeam.apparchitecturelibs.domain.AddNote;
import es.albertopeam.apparchitecturelibs.domain.LoadNotes;

import static es.albertopeam.apparchitecturelibs.data.DatabaseSingleton.instance;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 27/05/2017.
 */

public class DatabaseFactory {


    public static LoadNotes provideLoadNotes(){
        return new LoadNotesImpl(instance());
    }


    public static AddNote provideAddNote(){
        return new AddNoteImpl(instance());
    }
}
