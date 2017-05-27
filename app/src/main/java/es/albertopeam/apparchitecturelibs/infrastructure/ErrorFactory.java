package es.albertopeam.apparchitecturelibs.infrastructure;

/**
 * Created by Al on 25/05/2017.
 */

public class ErrorFactory {

    //TODO: needed add delegates to handle diferent exceptions...

    public Error provide(Exception exception){
        throw new UnsupportedOperationException();
        /*exception.printStackTrace();
        return new Error();*/
    }
}
