package com.cmbchina.ccd.pluto.trpc.rpc.handler.tcp;

import com.cmbchina.ccd.pluto.trpc.rpc.codec.RpcDecoder;
import com.cmbchina.ccd.pluto.trpc.rpc.codec.RpcEncoder;
import com.cmbchina.ccd.pluto.trpc.rpc.model.RpcRequest;
import com.cmbchina.ccd.pluto.trpc.rpc.model.RpcResponse;
import com.cmbchina.ccd.pluto.trpc.rpc.serializer.RpcSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.AllArgsConstructor;

import static com.cmbchina.ccd.pluto.trpc.common.constants.CodecConstants.LENGTH_FRAME_BYTES;

/**
 * Created by z674095 on 2019/3/28.
 */
@AllArgsConstructor
public class TcpClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RpcSerializer rpcSerializer;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, LENGTH_FRAME_BYTES));
        pipeline.addLast(new RpcDecoder(rpcSerializer, RpcResponse.class));
        pipeline.addLast(new LengthFieldPrepender(LENGTH_FRAME_BYTES));
        pipeline.addLast(new RpcEncoder(rpcSerializer, RpcRequest.class));
        pipeline.addLast(TcpClientHandler.get());
    }

}