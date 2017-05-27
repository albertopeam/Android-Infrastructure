package es.albertopeam.apparchitecturelibs.domain;

import es.albertopeam.apparchitecturelibs.infrastructure.UseCase;

/**
 * Created by Al on 25/05/2017.
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
