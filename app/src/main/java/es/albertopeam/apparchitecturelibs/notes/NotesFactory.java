package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.ViewModelProviders;

/**
 * Created by Al on 22/05/2017.
 */

class NotesFactory {

    static NotesPresenter provide(NotesActivity activity){
        NotesViewModel model = ViewModelProviders.of(activity).get(NotesViewModel.class);
        NotesRepository notesRepository = new NotesRepository();
        LoadNotesUseCase loadNotesUseCase = new LoadNotesUseCase(notesRepository);
        AddNoteUseCase addNoteUseCase = new AddNoteUseCase(notesRepository);
        NotesPresenter presenter = new NotesPresenter(activity, model, loadNotesUseCase, addNoteUseCase);
        return presenter;
    }
}
