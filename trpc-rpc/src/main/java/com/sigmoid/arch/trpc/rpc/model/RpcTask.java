package com.sigmoid.arch.trpc.rpc.model;

import com.sigmoid.arch.trpc.common.enums.ExceptionTypeEnum;
import com.sigmoid.arch.trpc.common.exception.TRpcSystemException;
import com.sigmoid.arch.trpc.common.exception.TRpcServiceException;
import com.sigmoid.arch.trpc.rpc.serializer.JsonSerializer;
import com.sigmoid.arch.trpc.rpc.server.ServerBeanMapping;
import com.sigmoid.arch.trpc.rpc.utils.ReflectUtil;
import io.netty.channel.Channel;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Created by ShawnZk on 2019/3/27.
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
            StackTraceElement[] eStackTrace = e.getStackTrace();
            String[] exceptionStacks = new String[eStackTrace.length];
            for (int i = 0; i < eStackTrace.length; i++) {
                exceptionStacks[i] = eStackTrace[i].toString();
            }
            return RpcResponse.builder()
                    .requestId(requestId)
                    .exceptionTypeEnum(exceptionTypeEnum)
                    .exceptionMsg(e.toString())
                    .exceptionStacks(exceptionStacks)
                    .build();
        }

    }

    public String getRequestId() {
        return Optional.ofNullable(rpcRequest).orElseGet(() -> RpcRequest.EMPTY_REQUEST).getRequestId();
    }

}
