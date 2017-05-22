package es.albertopeam.apparchitecturelibs.notes;

import java.util.List;

/**
 * Created by Al on 22/05/2017.
 */

class AddNoteUseCase {

    interface Callback{
        void onAddedNote(String note);
        void onAddNoteError(String error);
    }

    private NotesRepository repository;


    AddNoteUseCase(NotesRepository repository) {
        this.repository = repository;
    }


    void addNote(String note, AddNoteUseCase.Callback callback){
        if (note.equalsIgnoreCase("")){
            callback.onAddNoteError("Note cannot be empty");
            return;
        }
        repository.addNote(note);
        callback.onAddedNote(note);
    }

}
