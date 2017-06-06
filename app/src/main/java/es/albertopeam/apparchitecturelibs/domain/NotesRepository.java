package es.albertopeam.apparchitecturelibs.domain;

import java.util.List;

/**
 * Created by Alberto Penas Amor on 27/05/2017.
 */

interface NotesRepository {
    List<String> loadNotes() throws Exception;
    void addNote(String note) throws Exception;
    void removeNote(String note) throws Exception;
}
