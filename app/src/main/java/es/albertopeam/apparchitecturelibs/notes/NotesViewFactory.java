package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.ViewModelProviders;

import com.google.common.collect.ImmutableList;

import es.albertopeam.apparchitecturelibs.domain.AddNoteUseCase;
import es.albertopeam.apparchitecturelibs.domain.LoadNotesUseCase;
import es.albertopeam.apparchitecturelibs.domain.NotesFactory;
import es.albertopeam.apparchitecturelibs.domain.RemoveNoteUseCase;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutor;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutorSingleton;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionControllerFactory;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegate;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegateFactory;

import static es.albertopeam.apparchitecturelibs.data.DatabaseFactory.provideAddNote;
import static es.albertopeam.apparchitecturelibs.data.DatabaseFactory.provideLoadNotes;

/**
 * Created by Al on 22/05/2017.
 */

class NotesViewFactory {

    static NotesPresenter provide(NotesActivity activity){
        NotesViewModel model = ViewModelProviders.of(activity).get(NotesViewModel.class);
        NotesFactory.init(model.getNotes(), provideLoadNotes(), provideAddNote());
        LoadNotesUseCase loadNotesUseCase = NotesFactory.provideLoadNotes();
        AddNoteUseCase addNoteUseCase = NotesFactory.provideAddNote();
        RemoveNoteUseCase removeNoteUseCase = NotesFactory.provideRemoveNote();
        ImmutableList<ExceptionDelegate>delegates = ExceptionDelegateFactory.provide(
                new UnsupportedOperationExceptionDelegate(activity));
        ExceptionController exceptionController = ExceptionControllerFactory.provide(delegates);
        UseCaseExecutor useCaseExecutor = UseCaseExecutorSingleton.instance(exceptionController);
        NotesPresenter presenter = new NotesPresenter(
                activity,
                model,
                useCaseExecutor,
                loadNotesUseCase,
                addNoteUseCase,
                removeNoteUseCase);
        return presenter;
    }



}
