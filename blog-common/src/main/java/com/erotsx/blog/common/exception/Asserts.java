package com.erotsx.blog.common.exception;


import com.erotsx.blog.common.api.ErrorCode;

public class Asserts {

    public static void fail(String msg) {
        throw new ApiException(msg);
    }

    public static void fail(ErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

}
