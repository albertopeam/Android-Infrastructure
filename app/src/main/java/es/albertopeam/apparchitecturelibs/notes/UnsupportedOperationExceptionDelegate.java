package es.albertopeam.apparchitecturelibs.notes;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;

import com.afollestad.materialdialogs.MaterialDialog;

import java.lang.ref.WeakReference;

import es.albertopeam.apparchitecturelibs.R;
import com.github.albertopeam.infrastructure.exceptions.Error;
import com.github.albertopeam.infrastructure.exceptions.ExceptionDelegate;

class UnsupportedOperationExceptionDelegate
        implements ExceptionDelegate {

    private WeakReference<Activity> activityWeakReference;


    UnsupportedOperationExceptionDelegate(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }


    @Override
    public boolean canHandle(Exception exception) {
        return exception instanceof UnsupportedOperationException;
    }

    @Override
    public Error handle(Exception exception) {
        return new Error() {
            @Override
            public boolean isRecoverable() {
                return true;
            }

            @Override
            public int messageReference() {
                return R.string.unsupported_operation_exception;
            }


            @Override
            public void recover() {
                new MaterialDialog.Builder(activityWeakReference.get())
                        .content(messageReference())
                        .positiveText("ok")
                        .show();
            }
        };
    }

    @Override
    public boolean belongsTo(LifecycleOwner lifecycleOwner) {
        return activityWeakReference.get() == null || lifecycleOwner == activityWeakReference.get();
    }
}