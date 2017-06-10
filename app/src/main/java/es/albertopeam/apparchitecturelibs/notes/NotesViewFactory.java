package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;

import com.google.common.collect.ImmutableList;

import es.albertopeam.apparchitecturelibs.domain.NotesSingleton;
import es.albertopeam.apparchitecturelibs.domain.NotesRepository;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutor;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutorSingleton;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionControllerFactory;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegate;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegateFactory;

import static es.albertopeam.apparchitecturelibs.data.DatabaseFactory.provideAddNote;
import static es.albertopeam.apparchitecturelibs.data.DatabaseFactory.provideLoadNotes;

/**
 * Created by Alberto Penas Amor on 22/05/2017.
 */

class NotesViewFactory {

    static NotesPresenter provide(NotesActivity activity){
        Lifecycle lifecycle = activity.getLifecycle();
        NotesViewModel model = ViewModelProviders.of(activity).get(NotesViewModel.class);
        NotesRepository notesRepository = NotesSingleton.instance(model.getNotes(), provideLoadNotes(), provideAddNote());
        LoadNotesUseCase loadNotesUseCase = new LoadNotesUseCase(lifecycle, notesRepository);
        AddNoteUseCase addNoteUseCase = new AddNoteUseCase(lifecycle, notesRepository);
        RemoveNoteUseCase removeNoteUseCase = new RemoveNoteUseCase(lifecycle, notesRepository);
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
