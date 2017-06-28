package es.albertopeam.apparchitecturelibs.domain;

import java.util.List;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 22/05/2017.
 * all methods are synchronized for avoid exceptions for concurrent modifications
 */

class NotesRepositoryImpl
    implements NotesRepository {


    private List<String>notes;
    private LoadNotes loadNotes;
    private AddNote addNote;


    NotesRepositoryImpl(List<String> notes,
                        LoadNotes loadNotes,
                        AddNote addNote) {
        this.notes = notes;
        this.loadNotes = loadNotes;
        this.addNote = addNote;
    }


    @Override
    public synchronized List<String> loadNotes() throws Exception{
        if (!notes.isEmpty()){
            return notes;
        }
        List<String>loaded = loadNotes.load();
        notes.clear();
        for (String note:loaded){
            notes.add(note);
        }
        return notes;
    }


    @Override
    public synchronized void addNote(String note) throws Exception{
        if (note.equalsIgnoreCase("")){
            throw new Exception("Note cannot be empty");
        }
        addNote.add(note);
        notes.add(note);
    }


    @Override
    public synchronized void removeNote(String note) throws Exception{
        throw new UnsupportedOperationException();
    }
}
