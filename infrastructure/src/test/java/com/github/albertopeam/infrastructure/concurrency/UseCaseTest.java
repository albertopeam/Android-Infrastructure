package com.github.albertopeam.infrastructure.concurrency;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;

import com.github.albertopeam.infrastructure.exceptions.ExceptionController;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UseCaseTest {

    private UseCase sut;
    @Mock
    LifecycleOwner mockLifecycleOwner;
    @Mock
    Lifecycle mockLifecycle;
    @Mock
    ExceptionController mockExceptionController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(mockLifecycleOwner.getLifecycle()).thenReturn(mockLifecycle);
        sut = new UseCase(mockExceptionController, mockLifecycleOwner) {
            @Override
            protected Object run(Object o) throws Exception {
                return null;
            }
        };
    }

    @Test
    public void givenWhenCreatedThenRegisteredAsObserver(){
        verify(mockLifecycleOwner).getLifecycle();
        verify(mockLifecycle).addObserver(any(LifecycleObserver.class));
        assertThat(sut.canRespond(), equalTo(false));
    }

    @Test
    public void givenWhenDestroyedThenMustNotRespond(){
        sut.destroy();
        assertThat(sut.canRespond(), equalTo(false));
        assertThat(sut.lifecycleOwner(), is(nullValue()));
    }

    @Test
    public void givenWhenResumedThenCanRespond(){
        sut.resume();
        assertThat(sut.canRespond(), equalTo(true));
    }

    @Test
    public void givenWhenCreatedThenLifecycleOwnerIsNotNull(){
        assertThat(sut.lifecycleOwner(), is(notNullValue()));
    }
}
