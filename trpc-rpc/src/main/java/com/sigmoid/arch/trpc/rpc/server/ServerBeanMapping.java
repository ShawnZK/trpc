package com.sigmoid.arch.trpc.rpc.server;

import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentMap;

import static com.sigmoid.arch.trpc.rpc.utils.RpcUtil.genServerBeanMappingKey;
import static com.sigmoid.arch.trpc.rpc.utils.RpcUtil.genServerMethodMappingKey;

/**
 * Created by ShawnZk on 2019/4/2.
 */
public class ServerBeanMapping {

    //interface name + method name + arg type names -> method
    private static ConcurrentMap<String, Method> serverMethods = Maps.newConcurrentMap();

    //interface name -> bean
    private static ConcurrentMap<String, Object> serverBeans = Maps.newConcurrentMap();

    public static void add(ServerBean serverBean) {
        Class<?> clazz = serverBean.getBeanClazz();
        String ifaceName = clazz.getName();
        for (Method method : clazz.getDeclaredMethods()) {
            String key = genServerMethodMappingKey(ifaceName, method.getName(), method.getParameterTypes());
            serverMethods.putIfAbsent(key, method);
        }
        //TODO only support one implement for one interface
        serverBeans.putIfAbsent(genServerBeanMappingKey(ifaceName), serverBean.getBean());
    }

    public static Method getMethod(String clazzName, String methodName, String[] argTypeNames) {
        return serverMethods.get(genServerMethodMappingKey(clazzName, methodName, argTypeNames));
    }

    public static Object getBean(String clazzName) {
        return serverBeans.get(genServerBeanMappingKey(clazzName));
    }

}
