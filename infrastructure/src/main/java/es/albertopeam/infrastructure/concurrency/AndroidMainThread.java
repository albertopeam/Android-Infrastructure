package es.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 09/06/2017.
 *
 * Used for change between any thread to android main thread
 */

interface AndroidMainThread {
    void execute(@NonNull Runnable runnable);
}
