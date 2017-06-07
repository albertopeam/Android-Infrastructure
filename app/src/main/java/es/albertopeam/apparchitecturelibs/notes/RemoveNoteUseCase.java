package es.albertopeam.apparchitecturelibs.notes;

import es.albertopeam.apparchitecturelibs.domain.NotesRepository;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCase;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */
public class RemoveNoteUseCase
        implements UseCase<String, String> {


    private NotesRepository repository;


    RemoveNoteUseCase(NotesRepository repository) {
        this.repository = repository;
    }


    @Override
    public String run(String note) throws Exception {
        repository.removeNote(note);
        return note;
    }
}
