package es.albertopeam.apparchitecturelibs.notes;

import android.app.Activity;

import com.afollestad.materialdialogs.MaterialDialog;

import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.Error;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionDelegate;

class UnsupportedOperationExceptionDelegate
        implements ExceptionDelegate {

    private Activity activity;


    UnsupportedOperationExceptionDelegate(Activity activity) {
        this.activity = activity;
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
            public String message() {
                return "Unsupported operation";
            }

            @Override
            public void recover() {
                new MaterialDialog.Builder(activity)
                        .content(message())
                        .positiveText("ok")
                        .show();
            }
        };
    }
}