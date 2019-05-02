package com.sigmoid.arch.trpc.common.exception;

/**
 * Created by ShawnZk on 2019/3/26.
 */
public class TRpcServiceException extends Exception {

    public TRpcServiceException(Throwable cause) {
        super(cause);
    }

    public TRpcServiceException(String message) {
        super(message);
    }

    public TRpcServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
