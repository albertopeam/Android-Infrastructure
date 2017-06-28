package es.albertopeam.infrastructure.exceptions;

import android.support.annotation.StringRes;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 28/05/2017.
 *
 * Represents an @{link Error} that isnt recoverable.
 */

class NotRecoverableError
        extends Error {


    private int reference;


    NotRecoverableError(@StringRes int reference) {
        this.reference = reference;
    }


    @Override
    public boolean isRecoverable() {
        return false;
    }

    @Override
    public int messageReference() {
        return reference;
    }

    @Override
    public void recover() {
        throw new UnsupportedOperationException();
    }
}
