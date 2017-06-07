package es.albertopeam.apparchitecturelibs.domain;

import java.util.List;

/**
 * Created by Alberto Penas Amor on 27/05/2017.
 */

public class NotesSingleton {


    private static NotesRepository notesRepository;


    public static NotesRepository instance(List<String> notes,
                            LoadNotes loadNotes,
                            AddNote addNote){
        if (notesRepository == null){
            notesRepository = new NotesRepositoryImpl(notes, loadNotes, addNote);
        }
        return notesRepository;
    }
}
