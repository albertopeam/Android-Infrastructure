package com.github.albertopeam.infrastructure.concurrency;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;



public class UseCaseTest {

    private UseCase sut;
    @Mock
    Lifecycle mockLifecycle;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        sut = new UseCase(mockLifecycle) {
            @Override
            protected Object run(Object o) throws Exception {
                return null;
            }
        };
    }

    @Test
    public void givenWhenCreatedThenRegisteredAsObserver(){
        verify(mockLifecycle).addObserver(any(LifecycleObserver.class));
        assertThat(sut.canRespond(), equalTo(false));
    }

    @Test
    public void givenWhenDestroyedThenMustNotRespond(){
        sut.destroy();
        assertThat(sut.canRespond(), equalTo(false));
    }

    @Test
    public void givenWhenResumedThenCanRespond(){
        sut.resume();
        assertThat(sut.canRespond(), equalTo(true));
    }
}
