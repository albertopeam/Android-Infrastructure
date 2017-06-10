package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

/**
 * Created by Al on 09/06/2017.
 *
 * Used for change between any thread to android main thread
 */

interface MainThread {
    void execute(Runnable runnable);
}
