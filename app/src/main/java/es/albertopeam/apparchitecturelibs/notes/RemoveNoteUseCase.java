package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import es.albertopeam.apparchitecturelibs.domain.NotesRepository;
import com.github.albertopeam.infrastructure.concurrency.UseCase;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 25/05/2017.
 */
class RemoveNoteUseCase
        extends UseCase<String, String> {


    private NotesRepository repository;


    RemoveNoteUseCase(@NonNull LifecycleOwner lifecycleOwner,
                      @NonNull NotesRepository repository) {
        super(lifecycleOwner);
        this.repository = repository;
    }


    @Override
    public String run(String note) throws Exception {
        repository.removeNote(note);
        return note;
    }
}
