package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

class ExceptionControllerImpl
        implements ExceptionController{


    private List<ExceptionDelegate> delegates;


    ExceptionControllerImpl(List<ExceptionDelegate> delegates) {
        this.delegates = delegates;
    }


    @Override
    public synchronized Error handle(Exception exception) {
        for (ExceptionDelegate delegate:delegates){
            if (delegate.canHandle(exception)){
                return delegate.handle(exception);
            }
        }
        return new NotHandledError();
    }


    @Override
    public synchronized void addDelegate(ExceptionDelegate delegate, Lifecycle lifecycle) {
        delegates.add(delegate);
        lifecycle.addObserver(new GenericLifecycleObserver() {
            @Override
            public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                stateChanged(lifecycleOwner, event);
            }

            @Override
            public Object getReceiver() {
                return null;
            }
        });
    }


    private synchronized void stateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event){
        if (event.compareTo(Lifecycle.Event.ON_DESTROY) == 0) {
            List<ExceptionDelegate>toDestroyExceptionDelegates = new ArrayList<>();
            for (ExceptionDelegate delegate:delegates){
                if (delegate.belongsTo(lifecycleOwner)){
                    toDestroyExceptionDelegates.add(delegate);
                }
            }
            delegates.removeAll(toDestroyExceptionDelegates);
        }
    }
}
