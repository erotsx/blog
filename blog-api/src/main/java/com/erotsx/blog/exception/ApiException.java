package com.erotsx.blog.exception;

import com.erotsx.blog.vo.ErrorCode;

public class ApiException extends RuntimeException {

    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String msg) {
        super(msg);
    }


    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}