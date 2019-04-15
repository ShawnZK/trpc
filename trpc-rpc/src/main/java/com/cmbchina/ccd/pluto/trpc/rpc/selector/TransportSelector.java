package com.cmbchina.ccd.pluto.trpc.rpc.selector;

import com.cmbchina.ccd.pluto.trpc.common.enums.TransportEnum;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcSystemException;
import com.cmbchina.ccd.pluto.trpc.rpc.handler.http.HttpServerChannelInitializer;
import com.cmbchina.ccd.pluto.trpc.rpc.handler.tcp.TcpClientChannelInitializer;
import com.cmbchina.ccd.pluto.trpc.rpc.handler.tcp.TcpServerChannelInitializer;
import com.cmbchina.ccd.pluto.trpc.rpc.serializer.RpcSerializer;
import io.netty.channel.ChannelInitializer;

/**
 * Created by z674095 on 2019/3/27.
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
