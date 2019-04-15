package com.cmbchina.ccd.pluto.trpc.registry;

import com.cmbchina.ccd.pluto.trpc.registry.event.RegistryEvent;

/**
 * Created by Z674095 on 2019/3/26.
 */
public class ZookeeperRegistryHandler implements RegistryHandler {

    @Override
    public void pub(RegistryEvent registryEvent) {

    }

    @Override
    public RegistryHandler sub(RegistryEvent registryEvent) {
        return null;
    }
}
