package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.util.Log;

import com.google.common.collect.ImmutableList;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.albertopeam.apparchitecturelibs.di.ActivityScope;
import es.albertopeam.apparchitecturelibs.domain.NotesRepository;
import es.albertopeam.apparchitecturelibs.domain.NotesSingleton;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutor;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutorSingleton;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionControllerFactory;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegate;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegateFactory;

import static es.albertopeam.apparchitecturelibs.data.DatabaseFactory.provideAddNote;
import static es.albertopeam.apparchitecturelibs.data.DatabaseFactory.provideLoadNotes;

/**
 * Created by Al on 17/06/2017.
 */
@ActivityScope
@Subcomponent(modules = {NotesActivitySubcomponent.NotesModule.class})
public interface NotesActivitySubcomponent {
    void inject(NotesActivity activity);

    @Module
    class NotesModule {
        NotesActivity activity;

        public NotesModule(NotesActivity notesActivity) {
            this.activity = notesActivity;
        }

        @Provides
        @ActivityScope
        NotesPresenter provide(UseCaseExecutor useCaseExecutor,
                               ExceptionController exceptionController){
            Lifecycle lifecycle = activity.getLifecycle();
            NotesViewModel model = ViewModelProviders.of(activity).get(NotesViewModel.class);
            NotesRepository notesRepository = NotesSingleton.instance(model.getNotes(), provideLoadNotes(), provideAddNote());
            LoadNotesUseCase loadNotesUseCase = new LoadNotesUseCase(lifecycle, notesRepository);
            AddNoteUseCase addNoteUseCase = new AddNoteUseCase(lifecycle, notesRepository);
            RemoveNoteUseCase removeNoteUseCase = new RemoveNoteUseCase(lifecycle, notesRepository);
            ExceptionDelegate delegate = new UnsupportedOperationExceptionDelegate(activity);
            exceptionController.addDelegate(delegate, lifecycle);
            return new NotesPresenter(
                    activity,
                    model,
                    useCaseExecutor,
                    loadNotesUseCase,
                    addNoteUseCase,
                    removeNoteUseCase);
        }
    }
}
