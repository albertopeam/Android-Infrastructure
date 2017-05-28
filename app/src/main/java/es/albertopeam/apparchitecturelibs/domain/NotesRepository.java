package es.albertopeam.apparchitecturelibs.domain;

import java.util.List;

/**
 * Created by Alberto Penas Amor on 27/05/2017.
 */

interface NotesRepository {
    List<String> loadNotes();
    void addNote(String note);
    void removeNote(String note);
}
