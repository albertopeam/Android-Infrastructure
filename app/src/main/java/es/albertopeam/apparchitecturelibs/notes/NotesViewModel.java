package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 22/05/2017.
 */
class NotesViewModel
        extends ViewModel{


    private MutableLiveData<List<String>> notes = new MutableLiveData<>();


    @NonNull
    List<String> getNotes() {
        List<String>noteList;
        if (isEmpty()){
            noteList = new ArrayList<>();
            setNotes(noteList);
        }else{
            noteList = notes.getValue();
        }
        return noteList;
    }


    void setNotes(@NonNull List<String>notesList) {
        notes.postValue(notesList);
    }


    boolean isEmpty(){
        return notes.getValue() == null;
    }
}
