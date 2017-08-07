package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.albertopeam.apparchitecturelibs.di.ActivityScope;
import es.albertopeam.apparchitecturelibs.domain.NotesRepository;
import es.albertopeam.apparchitecturelibs.domain.NotesSingleton;
import com.github.albertopeam.infrastructure.concurrency.UseCaseExecutor;
import com.github.albertopeam.infrastructure.exceptions.ExceptionController;
import com.github.albertopeam.infrastructure.exceptions.ExceptionDelegate;

import static es.albertopeam.apparchitecturelibs.data.DatabaseFactory.provideAddNote;
import static es.albertopeam.apparchitecturelibs.data.DatabaseFactory.provideLoadNotes;

/**
 * Created by Alberto Penas Amor on 17/06/2017.
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
        public NotesPresenter provide(NotesActivity notesActivity,
                                      UseCaseExecutor useCaseExecutor,
                                      ExceptionController exceptionController,
                                      NotesViewModel model,
                                      LoadNotesUseCase loadNotesUseCase,
                                      AddNoteUseCase addNoteUseCase,
                                      RemoveNoteUseCase removeNoteUseCase){
            ExceptionDelegate delegate = new UnsupportedOperationExceptionDelegate(activity);
            exceptionController.addDelegate(delegate, notesActivity.getLifecycle());
            return new NotesPresenter(
                    activity,
                    model,
                    useCaseExecutor,
                    loadNotesUseCase,
                    addNoteUseCase,
                    removeNoteUseCase);
        }

        @Provides
        @ActivityScope
        public  NotesActivity provideNotesActivity(){
            return activity;
        }

        @Provides
        @ActivityScope
        public NotesViewModel provideNotesViewModel(NotesActivity notesActivity){
            return ViewModelProviders.of(notesActivity).get(NotesViewModel.class);
        }

        @Provides
        @ActivityScope
        public NotesRepository provideNotesRepository(NotesViewModel model){
            return NotesSingleton.instance(model.getNotes(), provideLoadNotes(), provideAddNote());
        }

        @Provides
        @ActivityScope
        public LoadNotesUseCase provideLoadNotesUseCase(NotesActivity notesActivity,
                                                        NotesRepository notesRepository){
            return new LoadNotesUseCase(notesActivity, notesRepository);
        }

        @Provides
        @ActivityScope
        public AddNoteUseCase provideAddNoteUseCase(NotesActivity notesActivity,
                                                    NotesRepository notesRepository){
            return new AddNoteUseCase(notesActivity, notesRepository);
        }

        @Provides
        @ActivityScope
        public RemoveNoteUseCase provideRemoveNoteUseCase(NotesActivity notesActivity,
                                                          NotesRepository notesRepository){
            return new RemoveNoteUseCase(notesActivity, notesRepository);
        }
    }
}
