package com.sigmoid.arch.trpc.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sigmoid.arch.trpc.common.definition.EnvDefinition;
import com.sigmoid.arch.trpc.common.definition.RpcDefinition;
import com.sigmoid.arch.trpc.common.definition.ServerDefinition;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

/**
 * Created by ShawnZk on 2019/4/4.
 */
public class DemoServer {

    private static RpcDefinition buildRpcDefinition() {
        ServerDefinition serverDefinition = new ServerDefinition("local",
                "demo-service",
                Sets.newHashSet(),
                10, 10, 9090,
                "tcp",
                "json",
                1000);
        EnvDefinition envDefinition = new EnvDefinition("etcd", "127.0.0.1:9300");
        return new RpcDefinition(envDefinition, serverDefinition, Lists.newArrayList());
    }

    @Test
    public void testDemoServer() {
        RpcDefinition rpcDefinition = buildRpcDefinition();
        try {
            TRpcBooter.init(rpcDefinition);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        RpcDefinition rpcDefinition = buildRpcDefinition();
//        try {
//            TRpcBooter.init(rpcDefinition);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }

}
