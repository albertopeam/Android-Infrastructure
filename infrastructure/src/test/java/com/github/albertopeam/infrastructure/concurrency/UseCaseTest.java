package com.github.albertopeam.infrastructure.concurrency;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import com.github.albertopeam.infrastructure.exceptions.ExceptionController;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


public class UseCaseTest {

    private UseCase sut;
    @Mock
    LifecycleOwner mockLifecycleOwner;
    @Mock
    Lifecycle mockLifecycle;
    @Mock
    ExceptionController mockExceptionController;
    @Mock
    Lifecycle.State mockState;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(mockLifecycleOwner.getLifecycle()).thenReturn(mockLifecycle);
        when(mockLifecycle.getCurrentState()).thenReturn(mockState);
        sut = new UseCase(mockExceptionController, mockLifecycleOwner) {
            @Override
            protected Object run(Object o) throws Exception {
                return null;
            }
        };
    }

    @Test
    public void givenWhenInitializedThenCanRun(){
        when(mockLifecycle.getCurrentState()).thenReturn(Lifecycle.State.INITIALIZED);
        assertThat(sut.canRun(), is(true));
    }

    @Test
    public void givenWhenDestroyedThenCanNotRun(){
        when(mockLifecycle.getCurrentState()).thenReturn(Lifecycle.State.INITIALIZED);
        sut.destroy();
        assertThat(sut.canRun(), is(false));
    }

    @Test
    public void givenWhenInitializedThenCanRespond(){
        when(mockLifecycle.getCurrentState()).thenReturn(Lifecycle.State.INITIALIZED);
        assertThat(sut.canRespond(), is(true));
    }

    @Test
    public void givenWhenDestroyedThenCanNotRespond(){
        when(mockLifecycle.getCurrentState()).thenReturn(Lifecycle.State.INITIALIZED);
        sut.destroy();
        assertThat(sut.canRespond(), is(false));
    }
}
