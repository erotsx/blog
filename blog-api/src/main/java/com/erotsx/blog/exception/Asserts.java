package com.erotsx.blog.exception;

import com.erotsx.blog.vo.ErrorCode;

public class Asserts {

    public static void fail(String msg) {
        throw new ApiException(msg);
    }

    public static void fail(ErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

}
