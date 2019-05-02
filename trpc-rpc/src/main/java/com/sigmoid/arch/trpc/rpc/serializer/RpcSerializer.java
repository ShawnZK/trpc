package com.sigmoid.arch.trpc.rpc.serializer;

/**
 * Created by Z674095 on 2019/3/27.
 */
public interface RpcSerializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);

}
