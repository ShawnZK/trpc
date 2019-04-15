package com.cmbchina.ccd.pluto.trpc.rpc.model;

import com.cmbchina.ccd.pluto.trpc.common.enums.ExceptionTypeEnum;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcServiceException;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcSystemException;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcTimeoutException;
import com.cmbchina.ccd.pluto.trpc.rpc.serializer.JsonSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.cmbchina.ccd.pluto.trpc.common.constants.ClientExceptionConstants.FAIL_ON_REQUEST_DELIVER;

/**
 *
 * Created by Z674095 on 2019/3/26.
 */
@Slf4j
@Builder
@ToString
public class RpcRequest implements Serializable {

    @Getter
    private String requestId;

    @Getter
    private String[] argTypeNames;

    @Getter
    private String[] argJsons;

    @Getter
    private String ifaceName;

    @Getter
    private String methodName;

    @Getter
    private long requestTimestamp;

    @Getter
    private long respondTimestamp;

    @Getter
    transient private Class<?> returnType;

    @Getter
    transient private Object result;

    @Getter
    transient private ExceptionTypeEnum exceptionTypeEnum;

    @Getter
    transient private String exceptionMsg;

    transient private CountDownLatch countDownLatch;

    public void failOnDeliver() {
        log.error("RpcRequest [{}] fail on deliver", requestId);
        exceptionTypeEnum = ExceptionTypeEnum.SYSTEM_EXCEPTION;
        exceptionMsg = FAIL_ON_REQUEST_DELIVER + requestId;
        countDownLatch.countDown();
        return;
    }

    public Object waitForReply(long timeoutInMillis) throws TRpcServiceException, TRpcSystemException, TimeoutException {

        try {
            if (!countDownLatch.await(timeoutInMillis, TimeUnit.MILLISECONDS)) {
                throw new TRpcTimeoutException(String.format("RpcRequest [%s] timeout", requestId));
            }
        } catch (InterruptedException e) {
            exceptionTypeEnum = ExceptionTypeEnum.SYSTEM_EXCEPTION;
            throw new TRpcSystemException(
                    String.format("Interrupted during waiting for requestId [%s]", requestId), e);
        }
        if (exceptionTypeEnum.equals(ExceptionTypeEnum.SYSTEM_EXCEPTION)) {
            //TODO exception message create with stack trace info
            throw new TRpcSystemException(exceptionMsg);
        } else if (exceptionTypeEnum.equals(ExceptionTypeEnum.SERVICE_EXCEPTION)) {
            throw new TRpcServiceException(exceptionMsg);
        }
        return result;
    }

    public void updateOnReply(RpcResponse rpcResponse) {
        log.debug("RpcRequest [{}] on update", requestId);
        this.exceptionTypeEnum = rpcResponse.getExceptionTypeEnum();
        this.exceptionMsg = rpcResponse.getExceptionMsg();
        this.result = JsonSerializer.fromJson(rpcResponse.getResultJson(), returnType);
        this.respondTimestamp = System.currentTimeMillis();
        this.countDownLatch.countDown();
    }

    public static final RpcRequest EMPTY_REQUEST = RpcRequest.builder().requestId("EMPTY").build();

}
