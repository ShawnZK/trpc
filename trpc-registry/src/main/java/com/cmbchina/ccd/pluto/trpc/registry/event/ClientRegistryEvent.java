package com.cmbchina.ccd.pluto.trpc.registry.event;

import com.cmbchina.ccd.pluto.trpc.registry.RegistryTypes;

/**
 * Created by Z674095 on 2019/3/26.
 */
public class ClientRegistryEvent extends RegistryEvent {

    private String group;

    private String serviceName;

    public ClientRegistryEvent(RegistryTypes registryTypes, String group, String serviceName) {
        super(registryTypes);
        this.group = group;
        this.serviceName = serviceName;
    }

}
