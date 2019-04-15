package com.cmbchina.ccd.pluto.trpc.rpc.client;

import com.cmbchina.ccd.pluto.trpc.common.enums.ExceptionTypeEnum;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcSystemException;
import com.cmbchina.ccd.pluto.trpc.rpc.model.RpcRequest;
import com.cmbchina.ccd.pluto.trpc.rpc.model.RpcRequestsHolder;
import com.cmbchina.ccd.pluto.trpc.rpc.serializer.JsonSerializer;
import com.cmbchina.ccd.pluto.trpc.rpc.utils.IdUtil;
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
