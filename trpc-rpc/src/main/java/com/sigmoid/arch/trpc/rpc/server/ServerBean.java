package com.sigmoid.arch.trpc.rpc.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by ShawnZk on 2019/4/2.
 */
@AllArgsConstructor
public class ServerBean {

    @Getter
    private String clazzName;

    @Getter
    private Object bean;

}
