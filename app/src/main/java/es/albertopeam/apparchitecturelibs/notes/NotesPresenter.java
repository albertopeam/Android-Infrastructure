package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.Lifecycle;

import java.util.List;

import es.albertopeam.apparchitecturelibs.domain.AddNoteUseCase;
import es.albertopeam.apparchitecturelibs.domain.LoadNotesUseCase;
import es.albertopeam.apparchitecturelibs.domain.RemoveNoteUseCase;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.Callback;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutor;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.Error;

/**
 * Created by Alberto Penas Amor on 22/05/2017.
 */

class NotesPresenter {


    private NotesView view;
    private NotesViewModel model;
    private UseCaseExecutor useCaseExecutor;
    private LoadNotesUseCase loadNotesUC;
    private AddNoteUseCase addNoteUC;
    private RemoveNoteUseCase removeNoteUC;
    private Lifecycle lifecycle;


    NotesPresenter(NotesView view,
                   Lifecycle lifecycle,
                   NotesViewModel model,
                   UseCaseExecutor useCaseExecutor,
                   LoadNotesUseCase loadNotesUC,
                   AddNoteUseCase addNoteUC,
                   RemoveNoteUseCase removeNoteUseCase) {
        this.view = view;
        this.lifecycle = lifecycle;
        this.model = model;
        this.useCaseExecutor = useCaseExecutor;
        this.loadNotesUC = loadNotesUC;
        this.addNoteUC = addNoteUC;
        this.removeNoteUC = removeNoteUseCase;
    }


    void loadNotes(){
        useCaseExecutor.execute(null, loadNotesUC, lifecycle, new Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> notes) {
                view.onLoadedNotes(notes);
            }

            @Override
            public void onError(Error error) {
                if (error.isRecoverable()){
                    error.recover();
                }else {
                    view.showError(error.message());
                }
            }
        });
    }


    void addNote(String note){
        useCaseExecutor.execute(note, addNoteUC, lifecycle, new Callback<String>() {
            @Override
            public void onSuccess(String s) {
                view.onLoadedNotes(model.getNotes());
            }

            @Override
            public void onError(Error error) {
                if (error.isRecoverable()){
                    error.recover();
                }else {
                    view.showError(error.message());
                }
            }
        });
    }


    void removeNote(String note){
        useCaseExecutor.execute(note, removeNoteUC, lifecycle, new Callback<String>(){
            @Override
            public void onSuccess(String note) {
                view.onRemovedNote(note);
            }

            @Override
            public void onError(Error error) {
                if (error.isRecoverable()){
                    error.recover();
                }else {
                    view.showError(error.message());
                }
            }
        });
    }


    void cancel(){
        //useCaseExecutor.cancel(loadNotesUC, addNoteUC, removeNoteUC);
    }
}
