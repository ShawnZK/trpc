package com.sigmoid.arch.trpc.demo;

import com.sigmoid.arch.trpc.common.definition.RpcDefinition;
import com.sigmoid.arch.trpc.demo.api.DemoApi;
import com.sigmoid.arch.trpc.demo.api.DemoRequest;
import com.sigmoid.arch.trpc.demo.api.DemoResponse;
import com.sigmoid.arch.trpc.rpc.client.ClientContext;
import com.sigmoid.arch.trpc.core.TRpcBooter;
import com.google.common.collect.Lists;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by ShawnZk on 2019/4/11.
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
