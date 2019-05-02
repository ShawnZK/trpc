package com.sigmoid.arch.trpc.registry;

import com.sigmoid.arch.trpc.registry.event.RegistryEvent;

/**
 * Created by ShawnZk on 2019/3/26.
 */
public interface RegistryHandler {

    void pub(RegistryEvent registryEvent);

    RegistryHandler sub(RegistryEvent registryEvent);

}
