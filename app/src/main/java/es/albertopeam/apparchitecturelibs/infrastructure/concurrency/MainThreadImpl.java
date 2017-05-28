package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import android.os.Handler;
import android.os.Looper;

/**
 * MainThread represents ability to change the execution flow between background thread to UI thread
 */
class MainThreadImpl {


    private Handler handler;


    MainThreadImpl() {
        this.handler = new Handler(Looper.getMainLooper());
    }


    /**
     * Run the passed runnable in the UI thread
     * @param runnable
     */
    void run(Runnable runnable) {
        handler.post(runnable);
    }
}