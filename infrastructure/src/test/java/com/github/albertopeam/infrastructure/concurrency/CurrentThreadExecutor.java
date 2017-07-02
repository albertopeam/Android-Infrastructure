package com.github.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 09/06/2017.
 */
class CurrentThreadExecutor
    implements Executor{
    @Override
    public void execute(@NonNull Runnable command) {
        command.run();
    }
}
