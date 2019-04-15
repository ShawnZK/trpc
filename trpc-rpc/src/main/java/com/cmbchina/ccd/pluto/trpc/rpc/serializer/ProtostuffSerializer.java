package com.cmbchina.ccd.pluto.trpc.rpc.serializer;

/**
 * Created by z674095 on 2019/3/27.
 */
public class ProtostuffSerializer implements RpcSerializer {

    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return null;
    }

}
