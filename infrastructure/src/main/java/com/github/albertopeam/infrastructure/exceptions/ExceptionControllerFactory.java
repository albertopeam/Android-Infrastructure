package com.github.albertopeam.infrastructure.exceptions;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 *
 * This factory helps to build an ExceptionController. Its mandatory to pass an non null
 * collection of {@link ExceptionDelegate}
 */

public class ExceptionControllerFactory {

    /**
     * Builds an ExceptionController
     * @param delegates that will handle exceptions
     * @return ExceptionController
     */
    public static ExceptionController provide(@NonNull List<ExceptionDelegate> delegates){
        if (delegates == null){
            throw new NullPointerException("Delegates cannot be null");
        }
        return new ExceptionControllerImpl(delegates);
    }
}
