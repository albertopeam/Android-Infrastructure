package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

/**
 * Created by Al on 28/05/2017.
 */

class NotHandledError extends Error {


    @Override
    public boolean isRecoverable() {
        return false;
    }


    @Override
    public String message() {
        return "Not handled error.";
    }


    @Override
    public void recover() {
        throw new UnsupportedOperationException();
    }
}
