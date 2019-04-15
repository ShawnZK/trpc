package com.cmbchina.ccd.pluto.trpc.registry;

import com.cmbchina.ccd.pluto.trpc.registry.event.RegistryEvent;

/**
 * Created by Z674095 on 2019/3/26.
 */
public interface RegistryHandler {

    void pub(RegistryEvent registryEvent);

    RegistryHandler sub(RegistryEvent registryEvent);

}
