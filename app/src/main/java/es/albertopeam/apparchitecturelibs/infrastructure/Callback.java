package es.albertopeam.apparchitecturelibs.infrastructure;

/**
 * Created by Al on 25/05/2017.
 */

public interface Callback<Response> {
    void onSuccess(Response response);
    void onError(Error error);
}
