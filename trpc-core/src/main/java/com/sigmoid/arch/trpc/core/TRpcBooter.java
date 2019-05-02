package com.sigmoid.arch.trpc.core;

import com.sigmoid.arch.trpc.common.definition.ClientDefinition;
import com.sigmoid.arch.trpc.common.definition.EnvDefinition;
import com.sigmoid.arch.trpc.common.definition.RpcDefinition;
import com.sigmoid.arch.trpc.common.definition.ServerDefinition;
import com.sigmoid.arch.trpc.registry.RegistryHandler;
import com.sigmoid.arch.trpc.registry.RegistryHandlerFactory;
import com.sigmoid.arch.trpc.rpc.client.ClientContext;
import com.sigmoid.arch.trpc.rpc.client.RpcClientHolder;
import com.sigmoid.arch.trpc.rpc.server.RpcServer;

import java.net.UnknownHostException;
import java.util.List;

/**
 *
 * Created by z674095 on 2019/4/3.
 */
public class TRpcBooter {

    private static RegistryHandler init(EnvDefinition envDefinition) {
        return RegistryHandlerFactory.create(envDefinition.getRegistryType(), envDefinition.getAddrs());
    }

    private static void init(List<ClientDefinition> clientDefinitions) {
        RpcClientHolder.init(clientDefinitions);
        ClientContext.init(clientDefinitions);
    }

    private static void init(ServerDefinition serverDefinition, RegistryHandler registryHandler) throws UnknownHostException {
        if (serverDefinition == null || registryHandler == null) {
            return;
        }
        RpcServer rpcServer = new RpcServer(serverDefinition, registryHandler);
        rpcServer.start();
    }

    public static void init(RpcDefinition rpcDefinition) throws UnknownHostException {
        RegistryHandler registryHandler = init(rpcDefinition.getEnvDefinition());
        init(rpcDefinition.getClientDefinitions());
        init(rpcDefinition.getServerDefinition(), registryHandler);
    }

}
