package es.albertopeam.apparchitecturelibs.domain;

import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCase;

/**
 * Created by Alberto Penas Amor on 22/05/2017.
 */

public class AddNoteUseCase
        implements UseCase<String, String> {


    private NotesRepository repository;


    AddNoteUseCase(NotesRepository repository) {
        this.repository = repository;
    }


    @Override
    public String run(String note) throws Exception {
        repository.addNote(note);
        return note;
    }
}
