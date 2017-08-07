package com.github.albertopeam.infrastructure.exceptions;

import com.github.albertopeam.infrastructure.R;

/**
 * Created by Al on 06/08/2017.
 */

class DelegatesCollisionError extends Error {

    @Override
    public boolean isRecoverable() {
        return false;
    }

    @Override
    public int messageReference() {
        return R.string.collision_error;
    }

    @Override
    public void recover() {
        throw new UnsupportedOperationException();
    }
}
