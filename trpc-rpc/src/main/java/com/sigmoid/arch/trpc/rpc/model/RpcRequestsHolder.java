package com.sigmoid.arch.trpc.rpc.model;

import com.sigmoid.arch.trpc.common.exception.TRpcSystemException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * Created by ShawnZk on 2019/3/28.
 */
@Slf4j
public class RpcRequestsHolder {

    //requestId -> RpcRequest
    private static final ConcurrentMap<String, RpcRequest> requests = Maps.newConcurrentMap();

    public static void add(RpcRequest rpcRequest) {
        String requestId = rpcRequest.getRequestId();
        Optional.ofNullable(requests.putIfAbsent(requestId, rpcRequest)).ifPresent(r -> {
            throw new TRpcSystemException(
                    String.format("RpcRequest add with requestId %s already exists", requestId));
        });
    }

    public static RpcRequest removeById(String requestId) {
        return Optional.ofNullable(requests.remove(requestId)).orElseGet(() -> {
            log.error("RpcRequest remove [{}] does not exist", requestId);
            return null;
        });
    }

    public static RpcRequest getById(String requestId) {
        return Optional.ofNullable(requests.get(requestId)).orElseGet(() -> {
            log.error("RpcRequest get [{}] does not exist", requestId);
            return null;
        });
    }

}
