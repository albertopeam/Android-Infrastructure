package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

/**
 * Created by Al on 09/06/2017.
 */

public class CurrentAndroidMainThread
        implements AndroidMainThread {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
