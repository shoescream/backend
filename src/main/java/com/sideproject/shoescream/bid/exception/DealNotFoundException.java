package com.sideproject.shoescream.bid.exception;


import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class DealNotFoundException extends ApplicationException {

    public DealNotFoundException(ErrorCode errorCode) { super(errorCode);}
}
