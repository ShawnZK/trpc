package com.sigmoid.arch.trpc.rpc.codec;

import com.sigmoid.arch.trpc.rpc.serializer.RpcSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * Created by ShawnZk on 2019/3/27.
 */
@AllArgsConstructor
public class RpcEncoder extends MessageToByteEncoder {

    private RpcSerializer rpcSerializer;

    private Class<?> clazz;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

        if (clazz.isInstance(o)) {
            byte[] data = rpcSerializer.serialize(o);
            byteBuf.writeBytes(data);
        }

    }

}
