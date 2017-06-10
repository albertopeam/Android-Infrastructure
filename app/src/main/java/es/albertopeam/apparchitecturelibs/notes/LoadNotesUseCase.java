package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

import java.util.List;

import es.albertopeam.apparchitecturelibs.domain.NotesRepository;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCase;

/**
 * Created by Alberto Penas Amor on 22/05/2017.
 */

class LoadNotesUseCase
        extends UseCase<Void,List<String>> {


    private NotesRepository repository;


    LoadNotesUseCase(@NonNull Lifecycle lifecycle,
                     @NonNull NotesRepository repository) {
        super(lifecycle);
        this.repository = repository;
    }


    @Override
    public List<String> run(Void aVoid) throws Exception {
        return repository.loadNotes();
    }
}