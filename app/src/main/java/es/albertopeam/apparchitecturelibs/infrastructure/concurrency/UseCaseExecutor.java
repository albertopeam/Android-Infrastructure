package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import android.arch.lifecycle.Lifecycle;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */

public interface UseCaseExecutor {
    <Args, Response> void execute(final Args args,
                                    final UseCase<Args, Response> useCase,
                                    final Lifecycle lifecycle,
                                  final Callback<Response> callback);
    void cancel(UseCase... useCase);
}
