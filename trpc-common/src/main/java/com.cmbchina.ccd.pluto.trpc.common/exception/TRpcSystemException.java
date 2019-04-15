package com.cmbchina.ccd.pluto.trpc.common.exception;

/**
 * Created by Z674095 on 2019/3/26.
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
