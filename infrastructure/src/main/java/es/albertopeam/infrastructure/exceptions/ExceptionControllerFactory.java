package es.albertopeam.infrastructure.exceptions;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 28/05/2017.
 */

public class ExceptionControllerFactory {

    public static ExceptionController provide(@NonNull List<ExceptionDelegate> delegates){
        return new ExceptionControllerImpl(delegates);
    }
}
