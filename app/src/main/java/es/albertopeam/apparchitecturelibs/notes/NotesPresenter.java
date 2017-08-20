package es.albertopeam.apparchitecturelibs.notes;

import android.support.annotation.NonNull;

import java.util.List;

import com.github.albertopeam.infrastructure.concurrency.Callback;
import com.github.albertopeam.infrastructure.concurrency.UseCaseExecutor;
import com.github.albertopeam.infrastructure.exceptions.HandledException;

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
        useCaseExecutor.execute(null, loadNotesUC, new Callback<List<String>>() {
            @Override
            public void onSuccess(@NonNull List<String> notes) {
                view.endLoading();
                view.onLoadedNotes(notes);
            }

            @Override
            public void onException(@NonNull HandledException handledException) {
                view.endLoading();
                handledException.recover();
            }
        });
    }


    void addNote(String note){
        useCaseExecutor.execute(note, addNoteUC, new Callback<String>() {
            @Override
            public void onSuccess(@NonNull String s) {
                view.onLoadedNotes(model.getNotes());
            }

            @Override
            public void onException(@NonNull HandledException handledException) {
                handledException.recover();
            }
        });
    }


    void removeNote(String note){
        useCaseExecutor.execute(note, removeNoteUC, new Callback<String>(){
            @Override
            public void onSuccess(@NonNull String note) {
                view.onRemovedNote(note);
            }

            @Override
            public void onException(@NonNull HandledException handledException) {
                handledException.recover();
            }
        });
    }
}
