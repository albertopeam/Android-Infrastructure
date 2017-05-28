package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

/**
 * Created by Al on 25/05/2017.
 */
public abstract class Error {
    public abstract boolean isRecoverable();
    public abstract String message();
    public abstract void recover();
}
