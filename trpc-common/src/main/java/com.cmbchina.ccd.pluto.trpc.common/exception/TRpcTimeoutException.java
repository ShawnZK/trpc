package com.cmbchina.ccd.pluto.trpc.common.exception;

import java.util.concurrent.TimeoutException;

/**
 * Created by shuyang on 2019/4/15.
 */
public class TRpcTimeoutException extends TimeoutException {

    public TRpcTimeoutException(String message) {
        super(message);
    }

}
