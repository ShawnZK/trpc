package com.cmbchina.ccd.pluto.trpc.registry;

import com.cmbchina.ccd.pluto.trpc.common.enums.RegistryEnum;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcSystemException;

/**
 * Created by z674095 on 2019/4/4.
 */
public class RegistryHandlerFactory {

    public static RegistryHandler create(String registryType, String addrs) {
        if (registryType.toUpperCase().equals(RegistryEnum.ZK.name())) {
            return new ZookeeperRegistryHandler();
        } else if (registryType.toUpperCase().equals(RegistryEnum.ETCD.name())){
            return new EtcdRegistryHandler();
        } else {
            throw new TRpcSystemException(String.format("Invalid registry enum %s", registryType));
        }
    }

}
