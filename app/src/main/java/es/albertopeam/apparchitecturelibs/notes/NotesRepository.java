package es.albertopeam.apparchitecturelibs.notes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Al on 22/05/2017.
 */

class NotesRepository {


    private List<String>notes = new ArrayList<>();


    List<String> loadNotes(){
        return notes;
    }


    void addNote(String note){
        notes.add(note);
    }
}
