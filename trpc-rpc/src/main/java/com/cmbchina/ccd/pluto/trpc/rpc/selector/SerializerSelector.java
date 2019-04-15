package com.cmbchina.ccd.pluto.trpc.rpc.selector;

import com.cmbchina.ccd.pluto.trpc.common.enums.CodecEnum;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcSystemException;
import com.cmbchina.ccd.pluto.trpc.rpc.serializer.JsonSerializer;
import com.cmbchina.ccd.pluto.trpc.rpc.serializer.ProtostuffSerializer;
import com.cmbchina.ccd.pluto.trpc.rpc.serializer.RpcSerializer;

/**
 * Created by z674095 on 2019/3/27.
 */
public class SerializerSelector {

    private static final JsonSerializer jsonSerializer = new JsonSerializer();

    private static final ProtostuffSerializer protostuffSerializer = new ProtostuffSerializer();

    public static RpcSerializer select(String codec) {
        if (codec.toUpperCase().equals(CodecEnum.JSON.name())) {
            return jsonSerializer;
        } else if (codec.toUpperCase().equals(CodecEnum.PROTOSTUFF.name())){
            return protostuffSerializer;
        } else {
            throw new TRpcSystemException(String.format("Invalid codec protocol %s", codec));
        }
    }

}
