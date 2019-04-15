package com.cmbchina.ccd.pluto.trpc.rpc.handler.http;

import com.cmbchina.ccd.pluto.trpc.rpc.serializer.RpcSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

/**
 * Created by Z674095 on 2019/3/27.
 */
@AllArgsConstructor
public class HttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RpcSerializer rpcSerializer;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

    }
}
