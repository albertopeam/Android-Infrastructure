package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 01/05/2017.
 *
 * AndroidMainThread can change the execution flow between any thread to UI thread
 */
class AndroidMainThreadImpl
        implements AndroidMainThread {


    private Handler handler;


    AndroidMainThreadImpl() {
        this.handler = new Handler(Looper.getMainLooper());
    }


    /**
     * Run the passed runnable in the UI thread
     * @param runnable
     */
    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }

}