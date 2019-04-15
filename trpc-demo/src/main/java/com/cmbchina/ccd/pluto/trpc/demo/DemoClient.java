package com.cmbchina.ccd.pluto.trpc.demo;

import com.cmbchina.ccd.pluto.trpc.common.definition.RpcDefinition;
import com.cmbchina.ccd.pluto.trpc.demo.api.DemoApi;
import com.cmbchina.ccd.pluto.trpc.demo.api.DemoRequest;
import com.cmbchina.ccd.pluto.trpc.demo.api.DemoResponse;
import com.cmbchina.ccd.pluto.trpc.rpc.client.ClientContext;
import com.cmbchind.ccd.pluto.trpc.core.TRpcBooter;
import com.google.common.collect.Lists;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by z674095 on 2019/4/11.
 */
public class DemoClient {

    public static void main(String[] args) throws UnknownHostException {

        RpcDefinition rpcDefinition = DemoConfBuilder.buildClientConf();
        TRpcBooter.init(rpcDefinition);
        DemoApi client = ClientContext.getClient(DemoApi.class);

        DemoRequest.Inner i1 = new DemoRequest.Inner("i1", 1L);
        DemoRequest.Inner i2 = new DemoRequest.Inner("i2", 2L);
        ArrayList<DemoRequest.Inner> inners = Lists.newArrayList(i1, i2);
        DemoResponse trys = client.handle(new DemoRequest(1, "demo", false, inners));

        System.out.println("response " + trys);

    }

}
