package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Alberto Penas Amor on 20/06/2017.
 */

public class ExceptionControllerImplTest {


    @Mock
    private ExceptionDelegate mockDelegate;
    private ExceptionControllerImpl sut;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void givenEmptyDelegatesWhenHandleExceptionThenThrowNotHandled(){
        sut = new ExceptionControllerImpl(new ArrayList<ExceptionDelegate>());
        Error error = sut.handle(new Exception());
        assertThat(error, instanceOf(NotHandledError.class));
    }


    @Test
    public void givenNPEDelegateWhenHandleNPEExceptionThenReturnNotRecoverableError(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        delegates.add(mockDelegate);
        NullPointerException targetException = new NullPointerException();
        Error error = new NotRecoverableError("npe");
        when(mockDelegate.canHandle(targetException)).thenReturn(true);
        when(mockDelegate.handle(targetException)).thenReturn(error);
        sut = new ExceptionControllerImpl(delegates);
        Error resultError = sut.handle(targetException);
        assertThat(resultError, instanceOf(NotRecoverableError.class));
        assertThat(resultError, equalTo(error));
    }


    @Test
    public void givenScopedDelegateWhenAddedToExceptControllerThenAddedDelegateAndRegisterObserver(){
        List<ExceptionDelegate> delegates = new ArrayList<>();
        sut = new ExceptionControllerImpl(delegates);
        Lifecycle mockLifecycle = mock(Lifecycle.class);
        ExceptionDelegate exceptionDelegate = new NPExceptionDelegate();
        sut.addDelegate(exceptionDelegate, mockLifecycle);
        assertThat(delegates.size(), equalTo(1));
        assertThat(delegates.get(0), equalTo(exceptionDelegate));
    }

    //todo: test inner state changed -> removed only on onDestroy
    
}

