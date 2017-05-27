package es.albertopeam.apparchitecturelibs.domain;

import java.util.List;

import es.albertopeam.apparchitecturelibs.infrastructure.UseCase;

/**
 * Created by Al on 22/05/2017.
 */

public class LoadNotesUseCase
        implements UseCase<Void,List<String>> {


    private NotesRepository repository;


    public LoadNotesUseCase(NotesRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<String> run(Void aVoid) throws Exception {
        return repository.loadNotes();
    }
}
