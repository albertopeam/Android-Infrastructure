package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import es.albertopeam.apparchitecturelibs.R;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 28/05/2017.
 *
 * This class represens an Error that isnt in the list of delegates that handle exceptions. When
 * an @{link {@link es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCase} throws
 * this we must review if we are handling all the exceptions that our use cases can thrown.
 */

class NotHandledError extends Error {

    @Override
    public boolean isRecoverable() {
        return false;
    }


    @Override
    public int messageReference() {
        return R.string.not_handled_error;
    }


    @Override
    public void recover() {
        throw new UnsupportedOperationException();
    }
}
