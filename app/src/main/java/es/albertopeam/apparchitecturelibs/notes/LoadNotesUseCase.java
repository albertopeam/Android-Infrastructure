package es.albertopeam.apparchitecturelibs.notes;

import java.util.List;

/**
 * Created by Al on 22/05/2017.
 */

class LoadNotesUseCase {

    interface Callback{
        void onLoadNotes(List<String> notes);
    }

    private NotesRepository repository;


    LoadNotesUseCase(NotesRepository repository) {
        this.repository = repository;
    }


    void loadNotes(Callback callback){
        callback.onLoadNotes(repository.loadNotes());
    }
}
