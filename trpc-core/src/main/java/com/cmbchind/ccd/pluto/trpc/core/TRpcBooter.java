package com.cmbchind.ccd.pluto.trpc.core;

import com.cmbchina.ccd.pluto.trpc.common.definition.ClientDefinition;
import com.cmbchina.ccd.pluto.trpc.common.definition.EnvDefinition;
import com.cmbchina.ccd.pluto.trpc.common.definition.RpcDefinition;
import com.cmbchina.ccd.pluto.trpc.common.definition.ServerDefinition;
import com.cmbchina.ccd.pluto.trpc.registry.RegistryHandler;
import com.cmbchina.ccd.pluto.trpc.registry.RegistryHandlerFactory;
import com.cmbchina.ccd.pluto.trpc.rpc.client.ClientContext;
import com.cmbchina.ccd.pluto.trpc.rpc.client.RpcClientHolder;
import com.cmbchina.ccd.pluto.trpc.rpc.server.RpcServer;

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
