package com.github.albertopeam.infrastructure.concurrency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ExecutorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExecutorImplTest {

    private ExecutorImpl sut;
    @Mock
    ExecutorService mockExecutorService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        sut = new ExecutorImpl(mockExecutorService);
    }


    @Test
    public void givenCreatedWhenPostCodeThenExecuteInExecutorService(){
        sut.execute(mock(Runnable.class));
        verify(mockExecutorService).execute(any(Runnable.class));
    }


    @Test
    public void givenCreatedWhenShutdownThenShutdownExecutorService(){
        sut.shutdown();
        verify(mockExecutorService).shutdown();
    }
}
