package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

/**
 * Created by Alberto Penas Amor on 25/05/2017.
 */
public interface UseCase<Args, Response> {
    Response run(Args args) throws Exception;
}
