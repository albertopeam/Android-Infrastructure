package com.github.albertopeam.infrastructure.exceptions;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class FakeExceptionDelegate
        implements ExceptionDelegate {

    @Override
    public boolean canHandle(@NonNull Exception exception) {
        return exception instanceof NullPointerException;
    }

    @Override
    public HandledException handle(@NonNull Exception exception) {
        return null;
    }
}
