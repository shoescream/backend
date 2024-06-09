package com.sideproject.shoescream.product.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class ProductNotFoundException extends ApplicationException {

    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
