package com.sigmoid.arch.trpc.registry;

import com.sigmoid.arch.trpc.common.enums.RegistryEnum;
import com.sigmoid.arch.trpc.common.exception.TRpcSystemException;

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
