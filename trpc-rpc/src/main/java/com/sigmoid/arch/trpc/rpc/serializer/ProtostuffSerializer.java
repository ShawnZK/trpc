package com.sigmoid.arch.trpc.rpc.serializer;

/**
 * Created by ShawnZk on 2019/3/27.
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
