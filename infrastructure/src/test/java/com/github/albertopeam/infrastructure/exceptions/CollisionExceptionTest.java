package com.github.albertopeam.infrastructure.exceptions;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class CollisionExceptionTest {

    private CollisionException sut;

    @Test
    public void givenEmptyDelegatesWhenGetFulltraceThenReturnEmpty(){
        sut = new CollisionException(mock(RuntimeException.class), new ArrayList<ExceptionDelegate>());
        String trace = sut.fullTrace();
        assertThat(trace, equalTo(""));
    }

    @Test
    public void givenOneDelegateWhenGetFulltraceThenReturnDelegateClassName(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        ExceptionDelegate delegate = new FakeExceptionDelegate();
        delegates.add(delegate);
        RuntimeException runtimeException = new RuntimeException();
        sut = new CollisionException(runtimeException, delegates);
        String trace = sut.fullTrace();
        assertThat(trace, equalTo(FakeExceptionDelegate.class.getName()));
    }

    @Test
    public void givenTwoDelegateWhenGetFulltraceThenReturnDelegatesClassNameSeparatedByCommas(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        delegates.add(new FakeExceptionDelegate());
        delegates.add(new FakeExceptionDelegate());
        RuntimeException runtimeException = new RuntimeException();
        sut = new CollisionException(runtimeException, delegates);
        String trace = sut.fullTrace();
        String expected = FakeExceptionDelegate.class.getName()+", "+FakeExceptionDelegate.class.getName();
        assertThat(trace, equalTo(expected));
    }
}
