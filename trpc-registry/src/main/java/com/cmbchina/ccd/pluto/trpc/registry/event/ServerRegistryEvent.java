package com.cmbchina.ccd.pluto.trpc.registry.event;

import com.cmbchina.ccd.pluto.trpc.registry.RegistryTypes;
import com.google.common.net.HostAndPort;

/**
 * Created by Z674095 on 2019/3/26.
 */
public class ServerRegistryEvent extends RegistryEvent {

    private String group;

    private String serviceName;

    private HostAndPort hostAndPort;

    public ServerRegistryEvent(RegistryTypes registryTypes, String group, String serviceName, HostAndPort hostAndPort) {
        super(registryTypes);
        this.group = group;
        this.serviceName = serviceName;
        this.hostAndPort = hostAndPort;
    }

}
