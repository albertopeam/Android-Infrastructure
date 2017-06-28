package es.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 01/05/2017.
 *
 * Executor can be used to execute tasks in background threads
 */
class ExecutorImpl
        implements java.util.concurrent.Executor {


    private int NUM_THREADS = 0;
    private ExecutorService executor;


    /**
     * Returns a new ThreadExecutor with n threads where n is the number of processors availables
     */
    ExecutorImpl() {
        NUM_THREADS = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(NUM_THREADS);
    }


    /**
     * Returns a new ThreadExecutor
     * @param threads
     */
    ExecutorImpl(int threads) {
        if (threads < 1){
            throw new IllegalArgumentException("At least needed one thread");
        }
        NUM_THREADS = threads;
        executor = Executors.newFixedThreadPool(NUM_THREADS);
    }


    @Override
    public void execute(@NonNull Runnable runnable) {
        executor.execute(runnable);
    }


    void shutdown(){
        executor.shutdown();
    }
}