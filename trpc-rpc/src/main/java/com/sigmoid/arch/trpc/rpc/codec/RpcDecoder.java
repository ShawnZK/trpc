package com.sigmoid.arch.trpc.rpc.codec;

import com.sigmoid.arch.trpc.rpc.serializer.RpcSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.sigmoid.arch.trpc.common.constants.CodecConstants.LENGTH_FRAME_BYTES;

/**
 * Created by ShawnZk on 2019/3/27.
 */
@AllArgsConstructor
public class RpcDecoder extends ByteToMessageDecoder {

    private RpcSerializer rpcSerializer;

    private Class<?> clazz;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        if (byteBuf.readableBytes() < LENGTH_FRAME_BYTES) {
            return;
        }

        byteBuf.markReaderIndex();
        int length = byteBuf.readInt();
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Object obj = rpcSerializer.deserialize(data, clazz);
        list.add(obj);

    }

}
