package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */

class Task {
    UseCase useCase;
    boolean canceled = false;
    Task(UseCase useCase) {
        this.useCase = useCase;
    }
}
