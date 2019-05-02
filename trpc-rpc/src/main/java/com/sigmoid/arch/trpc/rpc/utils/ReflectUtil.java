package com.sigmoid.arch.trpc.rpc.utils;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by ShawnZk on 2019/4/11.
 */
public class ReflectUtil {

    //class name -> Class<?>
    private static ConcurrentMap<String, Class<?>> clazzSet = Maps.newConcurrentMap();

    public static Class<?> getClazzByName(String name) {
        return clazzSet.computeIfAbsent(name, (className) -> {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                return null;
            }
        });
    }

}
