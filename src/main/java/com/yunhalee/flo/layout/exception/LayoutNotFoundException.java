package com.yunhalee.flo.layout.exception;

import com.yunhalee.flo.common.exceptions.EntityNotFoundException;

public class LayoutNotFoundException extends EntityNotFoundException {

    public LayoutNotFoundException(String message) {
        super(message);
    }
}
