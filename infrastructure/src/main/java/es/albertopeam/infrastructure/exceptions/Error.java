package es.albertopeam.infrastructure.exceptions;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 25/05/2017.
 *
 * Base class that represents an Exception during the execution of an
 * {@link es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCase}.
 * An error can be recoverable, this means that this class will handle the process of recover via
 * {@link #recover()}. In case that the error isnt recoverable then the client must handle the Error
 * via {@link #messageReference()}
 */
public abstract class Error {
    /**
     * Check if this error can be recoverable or not
     * @return true if recoverable, false otherwise
     */
    public abstract boolean isRecoverable();

    /**
     * String reference to be handled by the client
     * @return a string reference
     */
    public abstract int messageReference();

    /**
     * Recover exception
     */
    public abstract void recover();
}
