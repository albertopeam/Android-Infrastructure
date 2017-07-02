package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

import es.albertopeam.apparchitecturelibs.domain.NotesRepository;
import com.github.albertopeam.infrastructure.concurrency.UseCase;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 22/05/2017.
 */

class AddNoteUseCase
        extends UseCase<String, String> {


    private NotesRepository repository;


    AddNoteUseCase(@NonNull Lifecycle lifecycle,
                   @NonNull NotesRepository repository) {
        super(lifecycle);
        this.repository = repository;
    }


    @Override
    public String run(String note) throws Exception {
        repository.addNote(note);
        return note;
    }
}
