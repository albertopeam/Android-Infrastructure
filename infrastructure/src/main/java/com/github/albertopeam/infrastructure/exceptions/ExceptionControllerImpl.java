package com.github.albertopeam.infrastructure.exceptions;

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
            throw new CollisionException(exception, targetDelegates);
        }
    }

}
