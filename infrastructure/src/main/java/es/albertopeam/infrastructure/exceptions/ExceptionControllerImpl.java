package es.albertopeam.infrastructure.exceptions;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 28/05/2017.
 *
 * Concrete implementation of {@link ExceptionController}
 */

class ExceptionControllerImpl
        implements ExceptionController{


    private List<ExceptionDelegate> delegates;


    ExceptionControllerImpl(@NonNull List<ExceptionDelegate> delegates) {
        this.delegates = delegates;
    }


    @Override
    public synchronized Error handle(@NonNull Exception exception) {
        for (ExceptionDelegate delegate:delegates){
            if (delegate.canHandle(exception)){
                return delegate.handle(exception);
            }
        }
        return new NotHandledError();
    }


    @Override
    public synchronized void addDelegate(@NonNull ExceptionDelegate delegate,
                                         @NonNull Lifecycle lifecycle) {
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


    private synchronized void stateChanged(@NonNull LifecycleOwner lifecycleOwner,
                                           @NonNull Lifecycle.Event event){
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
