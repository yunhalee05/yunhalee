package com.yunhalee.flo.product.exception;

import com.yunhalee.flo.common.exceptions.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
