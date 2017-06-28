package es.albertopeam.apparchitecturelibs.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 27/05/2017.
 */
@Entity
class Note {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String note;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
