package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

class NotRecoverableError
        extends Error {


    private String message;


    NotRecoverableError(@NonNull String message) {
        this.message = message;
    }


    @Override
    public boolean isRecoverable() {
        return false;
    }


    @Override
    public String message(){
        return message;
    }


    @Override
    public void recover() {
        throw new UnsupportedOperationException();
    }
}
