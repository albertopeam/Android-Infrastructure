package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.albertopeam.infrastructure.exceptions.ExceptionDelegate;
import com.github.albertopeam.infrastructure.exceptions.HandledException;

import es.albertopeam.apparchitecturelibs.R;

/**
 * Created by alberto.penas.amor on 22/10/17.
 */

public class AddNoteExceptionDelegate implements ExceptionDelegate {

    private NotesActivity activity;

    public AddNoteExceptionDelegate(NotesActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean canHandle(@NonNull Exception exception) {
        return exception.getMessage().equals("Note cannot be empty");
    }

    @Override
    public HandledException handle(@NonNull Exception exception) {
        return new HandledException(exception) {
            @Override
            public void recover() {
                activity.showError(R.string.cannot_add_note_exception);
            }
        };
    }

    @Override
    public boolean belongsTo(@Nullable LifecycleOwner lifecycleOwner) {
        return lifecycleOwner == activity;
    }
}
