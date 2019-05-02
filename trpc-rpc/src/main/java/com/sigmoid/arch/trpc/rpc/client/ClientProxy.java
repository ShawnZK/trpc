package com.sigmoid.arch.trpc.rpc.client;

import com.sigmoid.arch.trpc.common.enums.ExceptionTypeEnum;
import com.sigmoid.arch.trpc.common.exception.TRpcSystemException;
import com.sigmoid.arch.trpc.rpc.model.RpcRequest;
import com.sigmoid.arch.trpc.rpc.model.RpcRequestsHolder;
import com.sigmoid.arch.trpc.rpc.serializer.JsonSerializer;
import com.sigmoid.arch.trpc.rpc.utils.IdUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 *
 * Created by z674095 on 2019/3/28.
 */
@Slf4j
class ClientProxy {

    static Object buildProxy(Class<?> clazz, String serviceName, String group) {
        String ifaceName = clazz.getName();
        RpcClient rpcClient = RpcClientHolder.getRpcClient(ifaceName, serviceName, group);
        if (rpcClient == null) {
            throw new TRpcSystemException(String.format(
                    "No RpcClient found for interface %s service %s group %s", ifaceName, serviceName, group));
        }
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clazz},
                (proxy, method, args) -> {
                    String clazzName = clazz.getName();
                    String requestId = IdUtil.genUuid();
                    String[] argTypeNames = new String[method.getParameterCount()];
                    String[] argJsons = new String[method.getParameterCount()];
                    for (int i = 0; i < method.getParameterCount(); i++) {
                        argTypeNames[i] = args[i].getClass().getName();
                        argJsons[i] = JsonSerializer.toJson(args[i]);
                    }
                    RpcRequest rpcRequest = RpcRequest.builder()
                            .requestId(requestId)
                            .ifaceName(clazzName)
                            .methodName(method.getName())
                            .argTypeNames(argTypeNames)
                            .argJsons(argJsons)
                            .returnType(method.getReturnType())
                            .exceptionTypeEnum(ExceptionTypeEnum.NO_EXCEPTION)
                            .countDownLatch(new CountDownLatch(1))
                            .requestTimestamp(System.currentTimeMillis())
                            .build();
                    try {
                        return Optional.ofNullable(rpcClient.get()).orElseGet(() -> {
                            throw new TRpcSystemException(
                                    String.format("No RpcConnection for interface %s service %s group %s", ifaceName, serviceName, group));
                        }).send(rpcRequest).waitForReply(rpcClient.getTimeout());
                    } finally {
                        RpcRequestsHolder.removeById(requestId);
                    }
                });
    }

}
