package com.sigmoid.arch.trpc.rpc.client;

import com.sigmoid.arch.trpc.common.definition.ClientDefinition;
import com.sigmoid.arch.trpc.common.exception.TRpcSystemException;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.sigmoid.arch.trpc.rpc.utils.RpcUtil.genClientProxyMappingKey;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 *
 * Created by z674095 on 2019/3/28.
 */
public class ClientContext {

    //interface name + service name + group -> ClientProxy
    private static Map<String, Object> proxies = Maps.newConcurrentMap();

    //interface name -> List<client bean>, immutable during runtime
    private static Map<String, List<ClientBean>> clazzSet;

    private static boolean initialized = false;

    private static Lock lock = new ReentrantLock();

    public static void init(List<ClientDefinition> clientDefinitions) {
        if (initialized) {
            return;
        }
        lock.lock();
        try {
            clazzSet = clientDefinitions.stream()
                    .map(ClientBean::buildFromClientDefinition)
                    .flatMap(Collection::stream)
                    .collect(groupingBy(ClientBean::getIfaceName, toList()));
            initialized = true;
        } finally {
            lock.unlock();
        }
    }

    private static Object getOrGenerate(Class<?> clazz, ClientBean clientBean) {
        String serviceName = clientBean.getServiceName();
        String group = clientBean.getGroup();
        String mappingKey = genClientProxyMappingKey(serviceName, group, clazz.getName());
        return proxies.computeIfAbsent(mappingKey, (x) -> ClientProxy.buildProxy(clazz, serviceName, group));
    }

    public static <T> T getClient(Class<T> clazz) {
        String ifaceName = clazz.getName();
        List<ClientBean> clientBeans = clazzSet.get(ifaceName);
        if (CollectionUtils.isEmpty(clientBeans)) {
            throw new TRpcSystemException(String.format("No definition found for interface %s", ifaceName));
        }
        //build proxy for the first
        ClientBean clientBean = clientBeans.get(0);
        return (T) getOrGenerate(clazz, clientBean);
    }

    public static <T> T getClient(Class<T> clazz, String group) {
        String ifaceName = clazz.getName();
        List<ClientBean> clientBeans = clazzSet.get(ifaceName);
        if (CollectionUtils.isEmpty(clientBeans)) {
            throw new TRpcSystemException(String.format("No definition found for interface %s", ifaceName));
        }
        for (ClientBean clientBean :  clientBeans) {
            if (clientBean.getGroup().equals(group)) {
                return (T) getOrGenerate(clazz, clientBean);
            }
        }
        throw new TRpcSystemException(String.format("No proxy found for interface %s group %s", ifaceName, group));
    }

}
