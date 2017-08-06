package com.github.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alberto Penas Amor on 01/05/2017.
 *
 * Executor can be used to execute tasks in background threads
 */
class ExecutorImpl
        implements java.util.concurrent.Executor {


    private ExecutorService executor;


    /**
     * Returns a new ExecutorImpl with n threads where n is the number of processors availables
     */
    ExecutorImpl() {
        int threads = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(threads);
    }


    /**
     * Returns a new ExecutorImpl
     * @param threads that executor handle
     */
    ExecutorImpl(int threads) {
        if (threads < 1){
            throw new IllegalArgumentException("At least needed one thread");
        }
        executor = Executors.newFixedThreadPool(threads);
    }


    /**
     * Returns ExecutorImpl
     * @param executor that will run code in background
     */
    ExecutorImpl(@NonNull ExecutorService executor){
        this.executor = executor;
    }


    @Override
    public void execute(@NonNull Runnable runnable) {
        executor.execute(runnable);
    }


    void shutdown(){
        executor.shutdown();
    }
}