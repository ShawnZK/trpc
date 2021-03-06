package com.sigmoid.arch.trpc.common.exception;

/**
 * Created by ShawnZk on 2019/3/26.
 */
public class TRpcSystemException extends RuntimeException {

    public TRpcSystemException(Throwable cause) {
        super(cause);
    }

    public TRpcSystemException(String message) {
        super(message);
    }

    public TRpcSystemException(String message, Throwable cause) {
        super(message, cause);
    }

}
