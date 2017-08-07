package com.github.albertopeam.infrastructure.exceptions;


import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import com.github.albertopeam.infrastructure.R;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExceptionControllerImplTest {

    private ExceptionControllerImpl sut;
    @Mock
    ExceptionDelegate mockDelegate;
    @Mock
    LifecycleOwner mockLifecycleOwner;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void givenEmptyDelegatesWhenHandleExceptionThenThrowNotHandled(){
        sut = new ExceptionControllerImpl(new ArrayList<ExceptionDelegate>());
        Error error = sut.handle(new Exception(), mockLifecycleOwner);
        assertThat(error, instanceOf(NotHandledError.class));
    }


    @Test
    public void givenNPEDelegateWhenHandleNPEExceptionThenReturnNotRecoverableError(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        delegates.add(mockDelegate);
        NullPointerException targetException = new NullPointerException();
        Error error = new NotRecoverableError(R.string.npe_exception);
        when(mockDelegate.canHandle(targetException)).thenReturn(true);
        when(mockDelegate.handle(targetException)).thenReturn(error);
        sut = new ExceptionControllerImpl(delegates);
        Error resultError = sut.handle(targetException, mockLifecycleOwner);
        assertThat(resultError, instanceOf(NotRecoverableError.class));
        assertThat(resultError, equalTo(error));
        assertThat(resultError.messageReference(), equalTo(R.string.npe_exception));
    }


    @Test
    public void givenScopedDelegateWhenAddedToExceptControllerThenAddedDelegateAndRegisterObserver(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        sut = new ExceptionControllerImpl(delegates);
        Lifecycle mockLifecycle = mock(Lifecycle.class);
        ExceptionDelegate exceptionDelegate = mock(ExceptionDelegate.class);
        sut.addDelegate(exceptionDelegate, mockLifecycle);
        assertThat(delegates.size(), equalTo(1));
        assertThat(delegates.get(0), equalTo(exceptionDelegate));
    }


    @Test
    public void givenScopedDelegateWhenAddedToExceptionControllerAndThrowADestroyStateChangeFromLifecycleThenRemoveDelegate(){
        final LifecycleOwner mockOwner = mock(LifecycleOwner.class);
        final Lifecycle.Event event = Lifecycle.Event.ON_DESTROY;
        ExceptionDelegate delegate = mock(ExceptionDelegate.class);
        Lifecycle mockLifecycle = mock(Lifecycle.class);
        doAnswer(new Answer<GenericLifecycleObserver>() {
            @Override
            public GenericLifecycleObserver answer(InvocationOnMock invocation) throws Throwable {
                ((GenericLifecycleObserver) invocation.getArguments()[0]).onStateChanged(mockOwner, event);
                return null;
            }
        }).when(mockLifecycle).addObserver(any(GenericLifecycleObserver.class));
        when(delegate.belongsTo(mockOwner)).thenReturn(true);
        List<ExceptionDelegate> delegates = new ArrayList<>();
        sut = new ExceptionControllerImpl(delegates);
        sut.addDelegate(delegate, mockLifecycle);
        assertThat(delegates.size(), equalTo(0));
    }

    @Test
    public void givenTwoDelegatesThatHandlesSameExceptionAndBelongsToSameLifecycleWhenHandleExceptionThenReturnDelegateCollisionError(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        ExceptionDelegate mockDelegate1 = mock(ExceptionDelegate.class);
        ExceptionDelegate mockDelegate2 = mock(ExceptionDelegate.class);
        when(mockDelegate1.canHandle(any(Exception.class))).thenReturn(true);
        when(mockDelegate2.canHandle(any(Exception.class))).thenReturn(true);
        when(mockDelegate1.belongsTo(any(LifecycleOwner.class))).thenReturn(true);
        when(mockDelegate2.belongsTo(any(LifecycleOwner.class))).thenReturn(true);
        delegates.add(mockDelegate1);
        delegates.add(mockDelegate2);
        sut = new ExceptionControllerImpl(delegates);
        Exception mockException = mock(Exception.class);
        LifecycleOwner mockLifecycleOwner = mock(LifecycleOwner.class);
        Error error = sut.handle(mockException, mockLifecycleOwner);
        assertThat(error, instanceOf(DelegatesCollisionError.class));
    }

    @Test
    public void givenTwoDelegatesThatHandlesSameExceptionAndAnyBelongsToLifecycleWhenHandleExceptionThenReturnNotHandledError(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        ExceptionDelegate mockDelegate1 = mock(ExceptionDelegate.class);
        ExceptionDelegate mockDelegate2 = mock(ExceptionDelegate.class);
        when(mockDelegate1.canHandle(any(Exception.class))).thenReturn(true);
        when(mockDelegate2.canHandle(any(Exception.class))).thenReturn(true);
        when(mockDelegate1.belongsTo(any(LifecycleOwner.class))).thenReturn(false);
        when(mockDelegate2.belongsTo(any(LifecycleOwner.class))).thenReturn(false);
        delegates.add(mockDelegate1);
        delegates.add(mockDelegate2);
        sut = new ExceptionControllerImpl(delegates);
        Exception mockException = mock(Exception.class);
        LifecycleOwner mockLifecycleOwner = mock(LifecycleOwner.class);
        Error error = sut.handle(mockException, mockLifecycleOwner);
        assertThat(error, instanceOf(NotHandledError.class));
    }

    @Test
    public void givenTwoDelegatesThatHandlesSameExceptionAndOnlyOneBelongsToLifecycleWhenHandleExceptionThenReturnDelegateError(){
        Error mockError = mock(Error.class);
        List<ExceptionDelegate> delegates = new ArrayList<>();
        ExceptionDelegate mockDelegate1 = mock(ExceptionDelegate.class);
        ExceptionDelegate mockDelegate2 = mock(ExceptionDelegate.class);
        when(mockDelegate1.canHandle(any(Exception.class))).thenReturn(true);
        when(mockDelegate2.canHandle(any(Exception.class))).thenReturn(true);
        when(mockDelegate1.belongsTo(any(LifecycleOwner.class))).thenReturn(false);
        when(mockDelegate2.belongsTo(any(LifecycleOwner.class))).thenReturn(true);
        when(mockDelegate2.handle(any(Exception.class))).thenReturn(mockError);
        delegates.add(mockDelegate1);
        delegates.add(mockDelegate2);
        sut = new ExceptionControllerImpl(delegates);
        Exception mockException = mock(Exception.class);
        LifecycleOwner mockLifecycleOwner = mock(LifecycleOwner.class);
        Error error = sut.handle(mockException, mockLifecycleOwner);
        assertThat(error, equalTo(mockError));
    }
}

