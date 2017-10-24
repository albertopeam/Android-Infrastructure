package com.github.albertopeam.infrastructure.concurrency;

import android.arch.lifecycle.LifecycleOwner;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;


import com.github.albertopeam.infrastructure.exceptions.ExceptionController;
import com.github.albertopeam.infrastructure.exceptions.HandledException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class UseCaseExecutorImplTest {

    @Mock
    UseCase<String, String> mockUseCase;
    @Mock
    Callback<String> mockCallback;
    @Mock
    ExceptionController mockExceptionController;
    @Mock
    Tasks mockTasks;
    @Mock
    LifecycleOwner mockLifecycleOwner;
    @Captor
    ArgumentCaptor<String>successArgCaptor;
    @Captor
    ArgumentCaptor<HandledException>errorArgCaptor;
    private String mockArgs = "args";
    private String response = "response";
    private UseCaseExecutor useCaseExecutor;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Executor spyExecutor = spy(new CurrentThreadExecutor());
        AndroidMainThread spyAndroidMainThread = spy(new CurrentAndroidMainThread());
        useCaseExecutor = new UseCaseExecutorImpl(
                spyExecutor,
                spyAndroidMainThread,
                mockTasks);
    }


    @Test
    public void givenAddedAnUseCaseWhenAddItAgainBeforeEndThenReturnNotAddedToExecution(){
        when(mockTasks.alreadyAdded(mockUseCase)).thenReturn(false);
        when(mockUseCase.canRun()).thenReturn(true);
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        when(mockTasks.alreadyAdded(mockUseCase)).thenReturn(true);
        boolean addedTwice = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        assertThat(added, equalTo(true));
        assertThat(addedTwice, equalTo(false));
        verify(mockTasks, times(1)).addUseCase(mockUseCase);
    }


    @Test
    public void givenUseCaseWhenExecuteThenRespondSuccessCallback() throws Exception {
        when(mockUseCase.run(anyString())).thenReturn(response);
        when(mockUseCase.canRespond()).thenReturn(true);
        when(mockUseCase.canRun()).thenReturn(true);
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        verify(mockTasks, times(1)).addUseCase(mockUseCase);
        verify(mockCallback, times(1)).onSuccess(successArgCaptor.capture());
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
        assertThat(successArgCaptor.getValue(), equalTo(response));
        assertThat(added, equalTo(true));
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
    }


    @Test
    public void givenUseCaseWithDestroyedStateWhenExecuteingThenNotRespondToCallbackAndRemoveFromTasks() throws Exception {
        when(mockUseCase.run(anyString())).thenReturn(response);
        when(mockUseCase.canRespond()).thenReturn(false);
        when(mockUseCase.canRun()).thenReturn(true);
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        verify(mockTasks, times(1)).addUseCase(mockUseCase);
        assertThat(added, equalTo(true));
        verify(mockUseCase, times(1)).run(mockArgs);
        verifyZeroInteractions(mockCallback);
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
    }


    @Test
    public void givenUseCaseWhenExecuteThenRespondErrorCallback() throws Exception{
        HandledException mockHandledException = mock(HandledException.class);
        when(mockUseCase.lifecycleOwner()).thenReturn(mock(LifecycleOwner.class));
        when(mockUseCase.run(anyString())).thenThrow(mock(Exception.class));
        when(mockUseCase.canRespond()).thenReturn(true);
        when(mockExceptionController.handle(any(Exception.class), any(LifecycleOwner.class))).thenReturn(mockHandledException);
        when(mockUseCase.exceptionController()).thenReturn(mockExceptionController);
        when(mockUseCase.canRun()).thenReturn(true);
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        verify(mockTasks, times(1)).addUseCase(mockUseCase);
        assertThat(added, equalTo(true));
        verify(mockUseCase, times(1)).run(mockArgs);
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
        verify(mockCallback, times(1)).onException(errorArgCaptor.capture());
        assertThat(errorArgCaptor.getValue(), equalTo(mockHandledException));
    }


    @Test
    public void givenUseCaseWithDestroyedStateWhenExecutingThenNotRespondCallbackAndRemoveFromTasks() throws Exception{
        Exception mockException = mock(Exception.class);
        when(mockUseCase.run(anyString())).thenThrow(mockException);
        when(mockUseCase.canRespond()).thenReturn(false);
        when(mockUseCase.canRun()).thenReturn(true);
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        verify(mockTasks, times(1)).addUseCase(mockUseCase);
        assertThat(added, equalTo(true));
        verify(mockUseCase, times(1)).run(mockArgs);
        verifyZeroInteractions(mockCallback);
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
    }


    @Test
    public void givenUseCaseWithDestroyedStateWhenTryToExecuteThenNotRunUseCaseAndNotRespondToCallback() throws Exception{
        when(mockUseCase.canRun()).thenReturn(false);
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        verify(mockTasks, times(0)).addUseCase(mockUseCase);
        assertThat(added, equalTo(true));
        verify(mockUseCase, times(0)).run(mockArgs);
        verifyZeroInteractions(mockCallback);
        verify(mockTasks, times(0)).removeUseCase(mockUseCase);
    }
}
