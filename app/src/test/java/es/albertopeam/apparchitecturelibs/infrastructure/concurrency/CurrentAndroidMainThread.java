package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

/**
 * Created by Alberto Penas Amor on 09/06/2017.
 */

public class CurrentAndroidMainThread
        implements AndroidMainThread {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
