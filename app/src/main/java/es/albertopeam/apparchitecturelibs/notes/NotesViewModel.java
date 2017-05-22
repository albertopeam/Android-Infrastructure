package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by Al on 22/05/2017.
 */

class NotesViewModel
        extends ViewModel{


    private MutableLiveData<List<String>> notes = new MutableLiveData<>();


    List<String> getNotes() {
        List<String> noteList = notes.getValue();
        return noteList;
    }


    void setNotes(List<String>notesList) {
        notes.postValue(notesList);
    }


    void addNote(String note){
        List<String>notesList = notes.getValue();
        notesList.add(note);
        notes.setValue(notesList);
    }
}
