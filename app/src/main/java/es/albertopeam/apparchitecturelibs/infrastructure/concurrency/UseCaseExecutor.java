package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

/**
 * Created by Al on 25/05/2017.
 */

public interface UseCaseExecutor {
    <Args, Response> void execute(final Args args,
                        final UseCase<Args, Response> useCase,
                        final Callback<Response> callback);
    void cancel(UseCase... useCase);
}
