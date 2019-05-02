package com.sigmoid.arch.trpc.common.exception;

import java.util.concurrent.TimeoutException;

/**
 * Created by ShawnZk on 2019/4/15.
 */
public class TRpcTimeoutException extends TimeoutException {

    public TRpcTimeoutException(String message) {
        super(message);
    }

}
