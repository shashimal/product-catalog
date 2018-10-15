package com.bookingboss.productcatalog.exception;

import com.bookingboss.productcatalog.error.ErrorCode;

public class EmptyProductException extends Exception {

    private ErrorCode errorCode;

    public EmptyProductException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public EmptyProductException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public EmptyProductException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public EmptyProductException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }


}
