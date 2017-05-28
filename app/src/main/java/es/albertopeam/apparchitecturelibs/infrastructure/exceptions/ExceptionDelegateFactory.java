package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alberto Penas Amor on 28/05/2017.
 */

public class ExceptionDelegateFactory {


    private static List<ExceptionDelegate>delegates = new ArrayList<>();


    static {
        delegates.add(new NPExceptionDelegate());
    }


    /**
     * Provides a copy of the default delegates
     * @return a inmutable collection of exception delegates
     */
    public static List<ExceptionDelegate> provide(){
        return ImmutableList.copyOf(delegates);
    }


    /**
     * Provides a copy of the default delegates plus the passed elements
     * @param extras more delegates
     * @return a inmutable collection of exception delegates
     */
    public static ImmutableList<ExceptionDelegate>provide(ExceptionDelegate... extras){
        List<ExceptionDelegate>allDelegates = new ArrayList<>(delegates);
        allDelegates.addAll(Arrays.asList(extras));
        return ImmutableList.copyOf(allDelegates);
    }



}
