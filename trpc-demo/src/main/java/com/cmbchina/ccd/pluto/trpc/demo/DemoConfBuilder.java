package com.cmbchina.ccd.pluto.trpc.demo;

import com.cmbchina.ccd.pluto.trpc.common.definition.ClientDefinition;
import com.cmbchina.ccd.pluto.trpc.common.definition.EnvDefinition;
import com.cmbchina.ccd.pluto.trpc.common.definition.RpcDefinition;
import com.cmbchina.ccd.pluto.trpc.common.definition.ServerDefinition;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Created by z674095 on 2019/4/4.
 */
public class DemoConfBuilder {

    public static RpcDefinition buildServerConf() {
        EnvDefinition envDefinition = new EnvDefinition("ETCD", "99.47.149.25:9092");
        ServerDefinition serverDefinition = new ServerDefinition("local", "demo-service", Sets.newHashSet(), 10, 10, 9091, "tcp", "json");
        RpcDefinition rpcDefinition = new RpcDefinition(envDefinition, serverDefinition, Lists.newArrayList());
        return rpcDefinition;
    }

    public static RpcDefinition buildClientConf() {
        EnvDefinition envDefinition = new EnvDefinition("ETCD", "99.47.149.25:9092");
        ClientDefinition clientDefinition = new ClientDefinition("local", "demo-service",
                Sets.newHashSet("com.cmbchina.ccd.pluto.trpc.demo.api.DemoApi"), 1000, "tcp", "json", Sets.newHashSet("127.0.0.1:9091"));
        RpcDefinition rpcDefinition = new RpcDefinition(envDefinition, null, Lists.newArrayList(clientDefinition));
        return rpcDefinition;
    }

}
