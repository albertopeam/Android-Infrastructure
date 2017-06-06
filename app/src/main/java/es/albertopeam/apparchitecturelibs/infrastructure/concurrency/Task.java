package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */

class Task implements LifecycleObserver {


    UseCase useCase;
    boolean canceled = false;
    private String state = "";


    Task(@NonNull UseCase useCase,
         @NonNull Lifecycle lifecycle) {
        this.useCase = useCase;
        lifecycle.addObserver(this);
    }


    boolean canBeExecuted(){
        System.out.println("state: " + state);
        return state.equalsIgnoreCase("resumed");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create() {
        state = "created";
        canceled = false;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        state = "started";
        canceled = false;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume() {
        state = "resumed";
        canceled = false;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause() {
        state = "paused";
        canceled = true;
    }
}
