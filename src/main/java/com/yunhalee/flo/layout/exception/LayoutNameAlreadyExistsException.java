package com.yunhalee.flo.product.exception;

import com.yunhalee.flo.common.exceptions.BadRequestException;

public class ProductNameAlreadyExistsException extends BadRequestException {

    public ProductNameAlreadyExistsException(String message) {
        super(message);
    }
}
