package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */

class Task  {


    private UseCase useCase;


    Task(@NonNull UseCase useCase) {
        this.useCase = useCase;
    }

    public UseCase getUseCase() {
        return useCase;
    }
}
