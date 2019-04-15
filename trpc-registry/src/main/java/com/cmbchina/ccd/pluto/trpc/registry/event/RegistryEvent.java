package com.cmbchina.ccd.pluto.trpc.registry.event;

import com.cmbchina.ccd.pluto.trpc.registry.RegistryTypes;
import lombok.AllArgsConstructor;

/**
 * Created by Z674095 on 2019/3/26.
 */
@AllArgsConstructor
public abstract class RegistryEvent {

    private RegistryTypes registryTypes;

}
