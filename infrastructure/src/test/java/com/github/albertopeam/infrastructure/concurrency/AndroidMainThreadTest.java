package com.github.albertopeam.infrastructure.concurrency;

import android.os.Handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AndroidMainThreadTest {

    private AndroidMainThreadImpl sut;
    @Mock
    Handler mockHandler;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        sut = new AndroidMainThreadImpl(mockHandler);
    }


    @Test
    public void givenCreatedWhenPostCodeThenExecuteInHandler(){
        sut.execute(mock(Runnable.class));
        verify(mockHandler).post(any(Runnable.class));
    }
}
