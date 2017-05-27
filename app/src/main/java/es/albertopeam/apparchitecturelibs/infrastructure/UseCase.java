package es.albertopeam.apparchitecturelibs.infrastructure;

/**
 * Created by Al on 25/05/2017.
 */
public interface UseCase<Args, Response> {
    Response run(Args args) throws Exception;
}
