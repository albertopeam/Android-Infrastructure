package com.github.albertopeam.infrastructure.concurrency;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.albertopeam.infrastructure.exceptions.ExceptionController;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 *
 * Class used for execute async code. This class handle the activity or fragment {@link Lifecycle},
 * allowing to know if it can run and return depending on the state of the {@link Lifecycle}.
 * To send to execution a subclass of {@link UseCase} its needed to pass it to
 * {@link UseCaseExecutor#execute(Object, UseCase, Callback)}
 */
public abstract class UseCase<Args, Response>
        implements LifecycleObserver {


    private LifecycleOwner lifecycleOwner;
    private ExceptionController exceptionController;
    private LifecycleState state;


    protected UseCase(@NonNull ExceptionController exceptionController,
                      @NonNull LifecycleOwner lifecycleOwner){
        this.exceptionController = exceptionController;
        this.lifecycleOwner = lifecycleOwner;
        this.state = LifecycleState.UNKNOW;
        lifecycleOwner.getLifecycle().addObserver(this);
    }


    protected abstract Response run(Args args) throws Exception;


    @NonNull
    ExceptionController exceptionController() {
        return exceptionController;
    }


    boolean canRespond(){
        return state == LifecycleState.RESUMED;
    }


    boolean canRun(){
        return state == LifecycleState.CREATED || state == LifecycleState.STARTED || state == LifecycleState.RESUMED;
    }


    @Nullable
    LifecycleOwner lifecycleOwner(){
        return lifecycleOwner;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create() {
        state = LifecycleState.CREATED;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        state = LifecycleState.STARTED;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume() {
        state = LifecycleState.RESUMED;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause() {
        state = LifecycleState.PAUSED;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        state = LifecycleState.STOPPED;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy() {
        state = LifecycleState.DESTROYED;
        lifecycleOwner = null;
        exceptionController = null;
    }

}
