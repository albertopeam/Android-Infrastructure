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
 * {@link UseCaseExecutor#execute(Object, UseCase, SuccessCallback, ExceptionCallback)}
 */
public abstract class UseCase<Args, Response>
        implements LifecycleObserver {


    private LifecycleOwner lifecycleOwner;
    private ExceptionController exceptionController;
    private LifecycleState lifecycleState;


    protected UseCase(@NonNull ExceptionController exceptionController,
                      @NonNull LifecycleOwner lifecycleOwner){
        this.exceptionController = exceptionController;
        this.lifecycleOwner = lifecycleOwner;
        this.lifecycleOwner.getLifecycle().addObserver(this);
        this.lifecycleState = LifecycleState.INITIALIZED;
    }


    protected abstract Response run(Args args) throws Exception;


    @NonNull
    ExceptionController exceptionController() {
        return exceptionController;
    }


    boolean canRespond(){
        if (lifecycleOwner == null){
            return false;
        }
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        boolean isInitialized = lifecycle.getCurrentState().isAtLeast(Lifecycle.State.INITIALIZED);
        boolean isNotDestroyed = lifecycleState != LifecycleState.DESTROYED;
        return isInitialized && isNotDestroyed;
    }


    boolean canRun(){
        if (lifecycleOwner == null){
            return false;
        }
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        boolean isInitialized = lifecycle.getCurrentState().isAtLeast(Lifecycle.State.INITIALIZED);
        boolean isNotDestroyed = lifecycleState != LifecycleState.DESTROYED;
        return isInitialized && isNotDestroyed;
    }


    @Nullable
    LifecycleOwner lifecycleOwner(){
        return lifecycleOwner;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        lifecycleState = LifecycleState.DESTROYED;
        lifecycleOwner = null;
    }

}
