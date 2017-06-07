package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */
public abstract class UseCase<Args, Response> implements LifecycleObserver {


    private LifecycleState state;
    private enum LifecycleState {
        CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED;
    }


    protected UseCase(@NonNull Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }


    protected abstract Response run(Args args) throws Exception;


    LifecycleState getState(){
        return state;
    }


    boolean canRespond(){
        return state == LifecycleState.RESUMED;
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
    }

}
