package es.albertopeam.apparchitecturelibs.notes;

import java.util.List;

/**
 * Created by Al on 22/05/2017.
 */

class NotesPresenter {


    private NotesView view;
    private NotesViewModel model;
    private LoadNotesUseCase loadNotesUC;
    private AddNoteUseCase addNoteUC;


    NotesPresenter(NotesView view,
                   NotesViewModel model,
                   LoadNotesUseCase loadNotesUC,
                   AddNoteUseCase addNoteUC) {
        this.view = view;
        this.model = model;
        this.loadNotesUC = loadNotesUC;
        this.addNoteUC = addNoteUC;
    }


    void loadNotes(){
        loadNotesUC.loadNotes(new LoadNotesUseCase.Callback() {
            @Override
            public void onLoadNotes(List<String> notes) {
                view.onLoadedNotes(notes);
            }
        });
    }


    void addNote(String note){
        addNoteUC.addNote(note, new AddNoteUseCase.Callback() {
            @Override
            public void onAddedNote(String note) {
                view.onLoadedNotes(model.getNotes());
            }

            @Override
            public void onAddNoteError(String error) {
                view.showError(error);
            }
        });
    }
}
