package es.albertopeam.apparchitecturelibs.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import es.albertopeam.apparchitecturelibs.domain.AddNote;
import es.albertopeam.apparchitecturelibs.domain.LoadNotes;

/**
 * Created by Al on 27/05/2017.
 */

public class DatabaseFactory {


    private static AppDatabase appAppDatabase;


    public static void init(Context context){
        if (appAppDatabase == null) {
            appAppDatabase = Room.databaseBuilder(context, AppDatabase.class, "database").build();
        }
    }


    static AppDatabase provide(){
        if (appAppDatabase == null){
            throw new NullPointerException("Invoke first: init(Context context)");
        }
        return appAppDatabase;
    }


    public static LoadNotes provideLoadNotes(){
        return new LoadNotesImpl(provide());
    }


    public static AddNote provideAddNote(){
        return new AddNoteImpl(provide());
    }
}
