package es.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 *
 * A task represens an {@link UseCase} during its execution
 */

class Task  {


    private UseCase useCase;


    Task(@NonNull UseCase useCase) {
        this.useCase = useCase;
    }


    @NonNull UseCase getUseCase() {
        return useCase;
    }
}
