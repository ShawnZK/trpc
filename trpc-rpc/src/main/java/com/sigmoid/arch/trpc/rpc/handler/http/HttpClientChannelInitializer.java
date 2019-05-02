package com.sigmoid.arch.trpc.rpc.handler.http;

import com.sigmoid.arch.trpc.rpc.serializer.RpcSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

/**
 * Created by ShawnZk on 2019/3/28.
 */
@AllArgsConstructor
public class HttpClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RpcSerializer rpcSerializer;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

    }

}
