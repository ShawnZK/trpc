package com.sigmoid.arch.trpc.rpc.client;

import com.sigmoid.arch.trpc.common.definition.ClientDefinition;
import com.sigmoid.arch.trpc.common.exception.TRpcSystemException;
import com.sigmoid.arch.trpc.common.strategy.loadbalance.RoundRobinStrategy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Created by z674095 on 2019/3/28.
 */
public class RpcClientHolder {

    //interface name -> List<RpcClient>, immutable during runtime
    private static final Map<String, List<RpcClient>> rpcClients = Maps.newHashMap();

    private static final Lock lock = new ReentrantLock();

    private static boolean initialized = false;

    public static void init(List<ClientDefinition> clientDefinitions) {
        if (initialized) {
            return;
        }
        lock.lock();
        try {
            clientDefinitions.forEach(clientDefinition -> {
                String group = clientDefinition.getGroup();
                String serviceName = clientDefinition.getServiceName();
                RpcClient rpcClient = new RpcClient(group, serviceName, new RoundRobinStrategy(), clientDefinition);
                Optional.ofNullable(clientDefinition.getProviders()).ifPresent(ps -> ps.forEach(rpcClient::add));
                clientDefinition.getIfaceNames().forEach(ifaceName -> {
                    rpcClients.putIfAbsent(ifaceName, Lists.newArrayList());
                    List<RpcClient> ifaceClients = rpcClients.get(ifaceName);
                    Iterator<RpcClient> iterator = ifaceClients.iterator();
                    while (iterator.hasNext()) {
                        if (rpcClient.equals(iterator.next())) {
                            throw new TRpcSystemException(String.format("Duplicate interface " +
                                    "definition %s for service %s and group %s", ifaceName, serviceName, group));
                        }
                    }
                    ifaceClients.add(rpcClient);
                });
            });
            initialized = true;
        } finally {
            lock.unlock();
        }
    }

    static RpcClient getRpcClient(String ifaceName, String serviceName, String group) {
        List<RpcClient> ifaceClients = rpcClients.get(ifaceName);
        if (CollectionUtils.isEmpty(ifaceClients)) {
            return null;
        }
        for (RpcClient rpcClient : ifaceClients) {
            if (rpcClient.getGroup().equals(group) && rpcClient.getServiceName().equals(serviceName)) {
                return rpcClient;
            }
        }
        return null;
    }

}
