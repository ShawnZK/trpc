package com.cmbchina.ccd.pluto.trpc.rpc.model;

import com.cmbchina.ccd.pluto.trpc.common.enums.ExceptionTypeEnum;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcSystemException;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcServiceException;
import com.cmbchina.ccd.pluto.trpc.rpc.serializer.JsonSerializer;
import com.cmbchina.ccd.pluto.trpc.rpc.server.ServerBeanMapping;
import com.cmbchina.ccd.pluto.trpc.rpc.utils.ReflectUtil;
import io.netty.channel.Channel;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.Callable;

import static com.cmbchina.ccd.pluto.trpc.rpc.model.RpcRequest.EMPTY_REQUEST;

/**
 *
 * Created by z674095 on 2019/3/27.
 */
public class RpcTask implements Callable<RpcResponse> {

    @Getter
    private Channel channel;

    private RpcRequest rpcRequest;

    public RpcTask(Channel channel, RpcRequest rpcRequest) {
        this.channel = channel;
        this.rpcRequest = rpcRequest;
    }

    private Object[] buildArgs(String[] argTypeNames, String[] argJsons) {
        if (argJsons.length != argTypeNames.length) {
            throw new TRpcSystemException("Miss match of arg information");
        }
        Object[] result = new Object[argJsons.length];
        for (int i = 0; i < argJsons.length; i++) {
            result[i] = JsonSerializer.fromJson(argJsons[i], ReflectUtil.getClazzByName(argTypeNames[i]));
        }
        return result;
    }

    @Override
    public RpcResponse call() throws Exception {

        String ifaceName = rpcRequest.getIfaceName();
        String methodName = rpcRequest.getMethodName();
        String[] argTypeNames = rpcRequest.getArgTypeNames();
        String[] argJsons = rpcRequest.getArgJsons();

        String requestId = rpcRequest.getRequestId();
        try {
            Method method = ServerBeanMapping.getMethod(ifaceName, methodName, argTypeNames);
            Object bean = ServerBeanMapping.getBean(ifaceName);
            if (method == null || bean == null) {
                throw new IllegalArgumentException(
                        String.format("Unable to invoke requestId [%s] interface [%s] method [%s]",
                                rpcRequest.getRequestId(), ifaceName, methodName));
            }
            Object result = method.invoke(bean, buildArgs(argTypeNames, argJsons));
            return RpcResponse.builder()
                    .requestId(requestId)
                    .exceptionTypeEnum(ExceptionTypeEnum.NO_EXCEPTION)
                    .resultJson(JsonSerializer.toJson(result))
                    .build();
        } catch (Exception e) {
            ExceptionTypeEnum exceptionTypeEnum = ExceptionTypeEnum.SYSTEM_EXCEPTION;
            if (e instanceof TRpcServiceException) {
                exceptionTypeEnum = ExceptionTypeEnum.SERVICE_EXCEPTION;
            }
            return RpcResponse.builder()
                    .requestId(requestId)
                    .exceptionTypeEnum(exceptionTypeEnum)
                    .exceptionMsg(e.toString())
                    .build();
        }

    }

    public String getRequestId() {
        return Optional.ofNullable(rpcRequest).orElseGet(() -> EMPTY_REQUEST).getRequestId();
    }

}
