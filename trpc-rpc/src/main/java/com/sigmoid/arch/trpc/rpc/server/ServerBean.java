package com.sigmoid.arch.trpc.rpc.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by z674095 on 2019/4/2.
 */
@AllArgsConstructor
public class ServerBean {

    @Getter
    private Class<?> beanClazz;

    @Getter
    private Object bean;

}
