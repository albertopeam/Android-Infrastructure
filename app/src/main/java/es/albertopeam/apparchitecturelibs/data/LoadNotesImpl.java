package es.albertopeam.apparchitecturelibs.data;

import java.util.ArrayList;
import java.util.List;

import es.albertopeam.apparchitecturelibs.domain.LoadNotes;

/**
 * Created by Al on 27/05/2017.
 */

class LoadNotesImpl
        implements LoadNotes {


    private AppDatabase appDatabase;


    LoadNotesImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }


    @Override
    public List<String> load() {
        List<Note>notes = appDatabase.notesDao().getAll();
        List<String>noteStrings = new ArrayList<>();
        if (notes != null && !notes.isEmpty()){
            for (Note note:notes){
                noteStrings.add(note.getNote());
            }
        }
        return noteStrings;
    }


}
