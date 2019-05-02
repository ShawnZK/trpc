package com.sigmoid.arch.trpc.rpc.handler.tcp;

import com.sigmoid.arch.trpc.rpc.codec.RpcDecoder;
import com.sigmoid.arch.trpc.rpc.codec.RpcEncoder;
import com.sigmoid.arch.trpc.rpc.model.RpcRequest;
import com.sigmoid.arch.trpc.rpc.model.RpcResponse;
import com.sigmoid.arch.trpc.rpc.serializer.RpcSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.AllArgsConstructor;

import static com.sigmoid.arch.trpc.common.constants.CodecConstants.LENGTH_FRAME_BYTES;

/**
 * Created by ShawnZk on 2019/3/26.
 */
@AllArgsConstructor
public class TcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RpcSerializer rpcSerializer;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, LENGTH_FRAME_BYTES));
        pipeline.addLast(new RpcDecoder(rpcSerializer, RpcRequest.class));
        pipeline.addLast(new LengthFieldPrepender(LENGTH_FRAME_BYTES));
        pipeline.addLast(new RpcEncoder(rpcSerializer, RpcResponse.class));
        pipeline.addLast(TcpServerHandler.get());
    }

}
