package com.bookingboss.productcatalog.exception;

import com.bookingboss.productcatalog.error.ErrorCode;

public class IdNotFoundException extends Exception {

    private ErrorCode errorCode;

    public IdNotFoundException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public IdNotFoundException(String message) {
        super(message);
    }

    public IdNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public IdNotFoundException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public IdNotFoundException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }


}
