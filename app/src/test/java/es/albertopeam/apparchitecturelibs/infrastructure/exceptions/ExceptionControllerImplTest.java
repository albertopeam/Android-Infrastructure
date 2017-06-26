package es.albertopeam.apparchitecturelibs.infrastructure.exceptions;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import es.albertopeam.apparchitecturelibs.R;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.doAnswer;
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
        Error error = new NotRecoverableError(R.string.null_pointer_exception);
        when(mockDelegate.canHandle(targetException)).thenReturn(true);
        when(mockDelegate.handle(targetException)).thenReturn(error);
        sut = new ExceptionControllerImpl(delegates);
        Error resultError = sut.handle(targetException);
        assertThat(resultError, instanceOf(NotRecoverableError.class));
        assertThat(resultError, equalTo(error));
        assertThat(resultError.messageReference(), equalTo(R.string.null_pointer_exception));
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
        }).when(mockLifecycle).addObserver(ArgumentMatchers.any(GenericLifecycleObserver.class));
        when(delegate.belongsTo(mockOwner)).thenReturn(true);
        List<ExceptionDelegate> delegates = new ArrayList<>();
        sut = new ExceptionControllerImpl(delegates);
        sut.addDelegate(delegate, mockLifecycle);
        assertThat(delegates.size(), equalTo(0));
    }
}

