package com.github.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 09/06/2017.
 */

class CurrentAndroidMainThread
        implements AndroidMainThread {
    @Override
    public void execute(@NonNull Runnable runnable) {
        runnable.run();
    }
}
