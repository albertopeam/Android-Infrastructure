package es.albertopeam.apparchitecturelibs.notes;

import com.github.albertopeam.infrastructure.concurrency.UseCaseExecutor;

class NotesPresenter {


    private NotesView view;
    private NotesViewModel model;
    private UseCaseExecutor useCaseExecutor;
    private LoadNotesUseCase loadNotesUC;
    private AddNoteUseCase addNoteUC;
    private RemoveNoteUseCase removeNoteUC;


    NotesPresenter(NotesView view,
                   NotesViewModel model,
                   UseCaseExecutor useCaseExecutor,
                   LoadNotesUseCase loadNotesUC,
                   AddNoteUseCase addNoteUC,
                   RemoveNoteUseCase removeNoteUseCase) {
        this.view = view;
        this.model = model;
        this.useCaseExecutor = useCaseExecutor;
        this.loadNotesUC = loadNotesUC;
        this.addNoteUC = addNoteUC;
        this.removeNoteUC = removeNoteUseCase;
    }


    void loadNotes(){
        view.loading();
        useCaseExecutor.execute(null, loadNotesUC, notes -> {
            view.endLoading();
            view.onLoadedNotes(notes);
        }, handledException -> {
            view.endLoading();
            handledException.recover();
        });
    }


    void addNote(String note){
        useCaseExecutor.execute(note, addNoteUC,
                notes -> view.onLoadedNotes(model.getNotes()),
                handledException -> handledException.recover());
    }


    void removeNote(String text){
        useCaseExecutor.execute(text, removeNoteUC,
                note -> view.onRemovedNote(note),
                handledException ->  handledException.recover());
    }
}
