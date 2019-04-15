package com.cmbchina.ccd.pluto.trpc.demo;

import com.cmbchina.ccd.pluto.trpc.common.definition.RpcDefinition;
import com.cmbchina.ccd.pluto.trpc.demo.api.DemoApi;
import com.cmbchina.ccd.pluto.trpc.demo.api.DemoRequest;
import com.cmbchina.ccd.pluto.trpc.demo.api.DemoResponse;
import com.cmbchina.ccd.pluto.trpc.rpc.server.ServerBean;
import com.cmbchina.ccd.pluto.trpc.rpc.server.ServerBeanMapping;
import com.cmbchind.ccd.pluto.trpc.core.TRpcBooter;

import java.net.UnknownHostException;

/**
 * Created by z674095 on 2019/4/11.
 */
public class DemoServer {

    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        ServerBeanMapping.add(new ServerBean(DemoApi.class, new DemoApi() {
            @Override
            public DemoResponse handle(DemoRequest demoRequest) {
                return DemoResponse.builder().code("back").count(-1).build();
            }
        }));
        RpcDefinition rpcDefinition = DemoConfBuilder.buildServerConf();
        TRpcBooter.init(rpcDefinition);
    }

}
