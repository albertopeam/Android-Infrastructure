package es.albertopeam.apparchitecturelibs.notes;

import java.util.List;

/**
 * Created by Alberto Penas Amor on 22/05/2017.
 */

interface NotesView {
    void onLoadedNotes(List<String> notes);
    void showError(String error);
    void onRemovedNote(String note);
    void loading();

    void endLoading();
}
