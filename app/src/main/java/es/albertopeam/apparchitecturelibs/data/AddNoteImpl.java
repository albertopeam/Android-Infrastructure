package es.albertopeam.apparchitecturelibs.data;

import es.albertopeam.apparchitecturelibs.domain.AddNote;

/**
 * Created by Alberto Penas Amor on 27/05/2017.
 */

class AddNoteImpl
        implements AddNote {


    private AppDatabase appDatabase;


    AddNoteImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }


    @Override
    public void add(String note) {
        Note aNote = new Note();
        aNote.setNote(note);
        appDatabase.notesDao().insert(aNote);
    }
}
