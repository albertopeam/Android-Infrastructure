package com.github.albertopeam.infrastructure.exceptions;


import android.arch.lifecycle.LifecycleOwner;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExceptionControllerImplTest {

    private ExceptionControllerImpl sut;
    @Mock
    ExceptionDelegate mockDelegate;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test(expected = NotHandledException.class)
    public void givenEmptyDelegatesWhenHandleExceptionThenThrowNotHandled(){
        sut = new ExceptionControllerImpl(new ArrayList<ExceptionDelegate>());
        sut.handle(new Exception());
    }


    @Test
    public void givenADelegateThatHandleExceptionWhenHandleThisExceptionThenReturnHandledException(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        delegates.add(mockDelegate);
        NullPointerException targetException = new NullPointerException();
        HandledException mockHandledException = mock(HandledException.class);
        when(mockDelegate.canHandle(targetException)).thenReturn(true);
        when(mockDelegate.handle(targetException)).thenReturn(mockHandledException);
        sut = new ExceptionControllerImpl(delegates);
        HandledException resultHandledException = sut.handle(targetException);
        assertThat(resultHandledException, equalTo(mockHandledException));
    }


    @Test(expected = CollisionException.class)
    public void givenTwoDelegatesThatHandlesSameExceptionWhenHandleExceptionThenThrowDelegateCollisionException(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        ExceptionDelegate mockDelegate1 = mock(ExceptionDelegate.class);
        ExceptionDelegate mockDelegate2 = mock(ExceptionDelegate.class);
        when(mockDelegate1.canHandle(any(Exception.class))).thenReturn(true);
        when(mockDelegate2.canHandle(any(Exception.class))).thenReturn(true);
        delegates.add(mockDelegate1);
        delegates.add(mockDelegate2);
        sut = new ExceptionControllerImpl(delegates);
        Exception mockException = mock(Exception.class);
        sut.handle(mockException);
    }


    @Test(expected = NotHandledException.class)
    public void givenTwoDelegatesThatNotHandlesAExceptionWhenThrowAExceptionThenReturnNotHandledException(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        ExceptionDelegate mockDelegate1 = mock(ExceptionDelegate.class);
        ExceptionDelegate mockDelegate2 = mock(ExceptionDelegate.class);
        when(mockDelegate1.canHandle(any(Exception.class))).thenReturn(false);
        when(mockDelegate2.canHandle(any(Exception.class))).thenReturn(false);
        delegates.add(mockDelegate1);
        delegates.add(mockDelegate2);
        sut = new ExceptionControllerImpl(delegates);
        Exception mockException = mock(Exception.class);
        sut.handle(mockException);
    }
}

