package es.albertopeam.infrastructure.concurrency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;


import es.albertopeam.infrastructure.exceptions.ExceptionController;
import es.albertopeam.infrastructure.exceptions.Error;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 09/06/2017.
 */

public class UseCaseExecutorImplTest {

    @Mock
    private UseCase<String, String> mockUseCase;
    @Mock
    private Callback<String>mockCallback;
    @Mock
    private ExceptionController mockExceptionController;
    @Mock
    private Tasks mockTasks;
    @Captor
    private ArgumentCaptor<String>successArgCaptor;
    @Captor
    private ArgumentCaptor<Error>errorArgCaptor;
    private Executor spyExecutor;
    private AndroidMainThread spyAndroidMainThread;
    private String mockArgs = "args";
    private String response = "response";
    private UseCaseExecutor useCaseExecutor;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        spyExecutor = spy(new CurrentThreadExecutor());
        spyAndroidMainThread = spy(new CurrentAndroidMainThread());
        useCaseExecutor = new UseCaseExecutorImpl(
                spyExecutor,
                spyAndroidMainThread,
                mockExceptionController,
                mockTasks);
    }


    @Test
    public void givenAddedAnUseCaseWhenAddItAgainBeforeEndThenReturnNotAddedToExecution(){
        when(mockTasks.alreadyAdded(mockUseCase)).thenReturn(false);
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
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        verify(mockTasks, times(1)).addUseCase(mockUseCase);
        verify(mockCallback, times(1)).onSuccess(successArgCaptor.capture());
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
        assertThat(successArgCaptor.getValue(), equalTo(response));
        assertThat(added, equalTo(true));
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
    }


    @Test
    public void givenUseCaseWithDestroyedStateWhenExecutedThenNotRespondToCallbackAndRemoveFromTasks() throws Exception {
        when(mockUseCase.run(anyString())).thenReturn(response);
        when(mockUseCase.canRespond()).thenReturn(false);
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        verify(mockTasks, times(1)).addUseCase(mockUseCase);
        assertThat(added, equalTo(true));
        verify(mockUseCase, times(1)).run(mockArgs);
        verifyZeroInteractions(mockCallback);
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
    }


    @Test
    public void givenUseCaseWhenExecuteThenRespondErrorCallback() throws Exception{
        Error mockError = mock(Error.class);
        Exception mockException = mock(Exception.class);
        when(mockUseCase.run(anyString())).thenThrow(mockException);
        when(mockUseCase.canRespond()).thenReturn(true);
        when(mockExceptionController.handle(mockException)).thenReturn(mockError);
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        verify(mockTasks, times(1)).addUseCase(mockUseCase);
        assertThat(added, equalTo(true));
        verify(mockUseCase, times(1)).run(mockArgs);
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
        verify(mockCallback, times(1)).onError(errorArgCaptor.capture());
        assertThat(errorArgCaptor.getValue(), equalTo(mockError));
    }


    @Test
    public void givenUseCaseWithDestroyedStateWhenExecutedThenNotRespondCallbackAndRemoveFromTasks() throws Exception{
        Exception mockException = mock(Exception.class);
        when(mockUseCase.run(anyString())).thenThrow(mockException);
        when(mockUseCase.canRespond()).thenReturn(false);
        boolean added = useCaseExecutor.execute(mockArgs, mockUseCase, mockCallback);
        verify(mockTasks, times(1)).addUseCase(mockUseCase);
        assertThat(added, equalTo(true));
        verify(mockUseCase, times(1)).run(mockArgs);
        verifyZeroInteractions(mockCallback);
        verify(mockTasks, times(1)).removeUseCase(mockUseCase);
    }
}
