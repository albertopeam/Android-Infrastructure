package es.albertopeam.apparchitecturelibs.infrastructure;

/**
 * Created by Al on 25/05/2017.
 */

class Task {
    UseCase useCase;
    boolean canceled = false;
    Task(UseCase useCase) {
        this.useCase = useCase;
    }
}
