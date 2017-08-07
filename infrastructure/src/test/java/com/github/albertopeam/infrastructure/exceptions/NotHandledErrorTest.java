package com.github.albertopeam.infrastructure.exceptions;

import com.github.albertopeam.infrastructure.R;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class NotHandledErrorTest {

    private NotHandledError sut;

    @Before
    public void setUp(){
        sut = new NotHandledError();
    }

    @Test
    public void givenNotHandledErrorWhenIsRecoverableThenReturnFalse(){
        boolean isRecoverable = sut.isRecoverable();
        assertThat(isRecoverable, equalTo(false));
    }

    @Test
    public void givenNotHandledErrorWhenGetMessageThenReturnDefault(){
        int ref = sut.messageReference();
        assertThat(ref, equalTo(R.string.not_handled_error));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenNotHandledErrorWhenRecoverThenThrowUnsupportedException(){
        sut.recover();
    }
}
