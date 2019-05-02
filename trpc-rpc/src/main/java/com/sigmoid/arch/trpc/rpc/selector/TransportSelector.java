package com.sigmoid.arch.trpc.rpc.selector;

import com.sigmoid.arch.trpc.common.enums.TransportEnum;
import com.sigmoid.arch.trpc.common.exception.TRpcSystemException;
import com.sigmoid.arch.trpc.rpc.handler.http.HttpServerChannelInitializer;
import com.sigmoid.arch.trpc.rpc.handler.tcp.TcpClientChannelInitializer;
import com.sigmoid.arch.trpc.rpc.handler.tcp.TcpServerChannelInitializer;
import com.sigmoid.arch.trpc.rpc.serializer.RpcSerializer;
import io.netty.channel.ChannelInitializer;

/**
 * Created by ShawnZk on 2019/3/27.
 */
public class TransportSelector {

    public static ChannelInitializer selectServerChannelInitializer(String transport, RpcSerializer rpcSerializer) {
        if (transport.toUpperCase().equals(TransportEnum.TCP.name())) {
            return new TcpServerChannelInitializer(rpcSerializer);
        } else if (transport.toUpperCase().equals(TransportEnum.HTTP.name())) {
            return new HttpServerChannelInitializer(rpcSerializer);
        } else {
            throw new TRpcSystemException(String.format("Invalid transport protocol %s", transport));
        }
    }

    public static ChannelInitializer selectClientChannelInitializer(String transport, RpcSerializer rpcSerializer) {
        if (transport.toUpperCase().equals(TransportEnum.TCP.name())) {
            return new TcpClientChannelInitializer(rpcSerializer);
        } else if (transport.toUpperCase().equals(TransportEnum.HTTP.name())) {
            return new HttpServerChannelInitializer(rpcSerializer);
        } else {
            throw new TRpcSystemException(String.format("Invalid transport protocol %s", transport));
        }
    }

}
