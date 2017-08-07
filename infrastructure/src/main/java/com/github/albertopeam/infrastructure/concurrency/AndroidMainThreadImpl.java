package com.github.albertopeam.infrastructure.concurrency;

import android.os.Handler;
import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 01/05/2017.
 *
 * AndroidMainThread can change the execution flow between any thread to UI thread
 */
class AndroidMainThreadImpl
        implements AndroidMainThread {


    private Handler handler;


    AndroidMainThreadImpl(Handler handler) {
        this.handler = handler;
    }


    /**
     * Run the passed runnable in the UI thread
     * @param runnable to execute in background thread
     */
    @Override
    public void execute(@NonNull Runnable runnable) {
        handler.post(runnable);
    }

}