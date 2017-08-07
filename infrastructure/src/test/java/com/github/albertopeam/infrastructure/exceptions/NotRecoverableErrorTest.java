package com.github.albertopeam.infrastructure.exceptions;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NotRecoverableErrorTest {

    private NotRecoverableError sut;
    private int ref = 0;

    @Before
    public void setUp(){
        sut = new NotRecoverableError(ref);
    }

    @Test
    public void givenNotRecoverableErrorWhenIsRecoverableThenReturnFalse(){
        boolean isRecoverable = sut.isRecoverable();
        assertThat(isRecoverable, equalTo(false));
    }

    @Test
    public void givenNotRecoverableErrorWhenGetMessageThenReturnRef(){
        int ref = sut.messageReference();
        assertThat(ref, equalTo(ref));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenNotRecoverableErrorWhenRecoverThenThrowUnsupportedException(){
        sut.recover();
    }
}
