package com.sigmoid.arch.trpc.registry.event;

import com.sigmoid.arch.trpc.registry.RegistryTypes;
import lombok.AllArgsConstructor;

/**
 * Created by ShawnZk on 2019/3/26.
 */
@AllArgsConstructor
public abstract class RegistryEvent {

    private RegistryTypes registryTypes;

}
