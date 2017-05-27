package es.albertopeam.apparchitecturelibs.domain;

import java.util.List;

/**
 * Created by Al on 27/05/2017.
 */

public class NotesFactory {


    private static NotesRepository notesRepository;


    public static void init(List<String> notes,
                            LoadNotes loadNotes,
                            AddNote addNote){
        if (notesRepository == null){
            notesRepository = new NotesRepositoryImpl(notes, loadNotes, addNote);
        }
    }


    public static LoadNotesUseCase provideLoadNotes(){
        checkNotNull();
        return new LoadNotesUseCase(notesRepository);
    }


    public static AddNoteUseCase provideAddNote(){
        checkNotNull();
        return new AddNoteUseCase(notesRepository);
    }


    public static RemoveNoteUseCase provideRemoveNote(){
        checkNotNull();
        return new RemoveNoteUseCase(notesRepository);
    }


    private static void checkNotNull() {
        if (notesRepository == null){
            throw new NullPointerException("NotesRepository isnt init. Invoke public static NotesRepository provide(List<String> notes) before");
        }
    }
}
