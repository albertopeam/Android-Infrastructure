package com.github.albertopeam.infrastructure.exceptions;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
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
    public HandledException handle(@NonNull Exception exception,
                                   @Nullable LifecycleOwner lifecycleOwner) {
        List<ExceptionDelegate>targetDelegates = new ArrayList<>();
        for (ExceptionDelegate delegate:delegates){
            if (delegate.canHandle(exception)){
                targetDelegates.add(delegate);
            }
        }
        if (targetDelegates.isEmpty()){
            throw new NotHandledException(exception);
        }else if (targetDelegates.size() == 1){
            ExceptionDelegate delegate = targetDelegates.get(0);
            return delegate.handle(exception);
        }else {
            List<ExceptionDelegate>belongsDelegates = new ArrayList<>();
            for (ExceptionDelegate delegate:targetDelegates){
                if (delegate.belongsTo(lifecycleOwner)){
                    belongsDelegates.add(delegate);
                }
            }
            if (belongsDelegates.isEmpty()){
                throw new NotHandledException(exception);
            }else if(belongsDelegates.size() == 1){
                ExceptionDelegate delegate = belongsDelegates.get(0);
                return delegate.handle(exception);
            }else{
                throw new CollisionException(exception, belongsDelegates);
            }
        }
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
        if (event.equals(Lifecycle.Event.ON_DESTROY)) {
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
