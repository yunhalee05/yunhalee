package com.yunhalee.flo.layout.exception;

import com.yunhalee.flo.common.exceptions.BadRequestException;

public class LayoutNameAlreadyExistsException extends BadRequestException {

    public LayoutNameAlreadyExistsException(String message) {
        super(message);
    }
}
