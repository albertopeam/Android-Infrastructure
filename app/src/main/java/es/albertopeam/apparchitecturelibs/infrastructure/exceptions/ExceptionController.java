package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import android.arch.lifecycle.Lifecycle;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

public interface ExceptionController {
    Error handle(Exception e);
    void addDelegate(ExceptionDelegate delegate, Lifecycle lifecycle);
}
