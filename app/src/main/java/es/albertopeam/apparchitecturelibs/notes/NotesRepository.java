package es.albertopeam.apparchitecturelibs.notes;

import java.util.List;

/**
 * Created by Al on 22/05/2017.
 */

class NotesRepository {


    private List<String>notes;


    NotesRepository(List<String> notes) {
        this.notes = notes;
    }

    List<String> loadNotes(){
        return notes;
    }


    void addNote(String note){
        notes.add(note);
    }
}
