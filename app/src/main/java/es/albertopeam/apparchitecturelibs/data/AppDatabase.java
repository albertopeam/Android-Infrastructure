package es.albertopeam.apparchitecturelibs.data;

import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase
        extends RoomDatabase {
    public abstract NotesDao notesDao();
}