package com.cmbchina.ccd.pluto.trpc.rpc.client.connection;

import com.cmbchina.ccd.pluto.trpc.common.definition.ClientDefinition;
import com.cmbchina.ccd.pluto.trpc.common.exception.TRpcSystemException;
import com.cmbchina.ccd.pluto.trpc.rpc.client.RpcClient;
import com.cmbchina.ccd.pluto.trpc.rpc.model.RpcRequest;
import com.cmbchina.ccd.pluto.trpc.rpc.model.RpcRequestsHolder;
import com.cmbchina.ccd.pluto.trpc.rpc.selector.SerializerSelector;
import com.cmbchina.ccd.pluto.trpc.rpc.selector.TransportSelector;
import com.cmbchina.ccd.pluto.trpc.rpc.utils.PlatformUtil;
import com.google.common.net.HostAndPort;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;

/**
 * Created by z674095 on 2019/3/29.
 */
@Slf4j
public class RpcConnection implements Closeable {

    private Bootstrap bootstrap;

    private Channel channel;

    private EventLoopGroup eventLoopGroup;

    private RpcClient rpcClient;

    @Getter
    private HostAndPort hostAndPort;

    private void initOptions() {
        //TODO
    }

    private void initHandlers(String transport, String codes) {
        ChannelInitializer channelInitializer = TransportSelector
                .selectClientChannelInitializer(transport, SerializerSelector.select(codes));
        bootstrap.handler(channelInitializer);
    }

    public RpcConnection(RpcClient rpcClient, ClientDefinition clientDefinition, HostAndPort hostAndPort) {

        this.rpcClient = rpcClient;
        this.hostAndPort = hostAndPort;
        this.eventLoopGroup = PlatformUtil.isOnLinux() ? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);
        this.bootstrap = new Bootstrap();

        try {
            bootstrap.group(eventLoopGroup)
                    .channel(PlatformUtil.isOnLinux() ? EpollSocketChannel.class : NioSocketChannel.class);
            initHandlers(clientDefinition.getTransport(), clientDefinition.getCodes());
            initOptions();
            bootstrap.remoteAddress(hostAndPort.getHost(), hostAndPort.getPort());
            ChannelFuture channelFuture = bootstrap.connect().addListener(future -> {
                if (future.isSuccess()) {
                    log.debug("Successfully to build RPC connection with service name {} group {} host {}",
                            clientDefinition.getServiceName(), clientDefinition.getGroup(), hostAndPort.toString());
                } else {
                    log.error("Fail to build RPC connection with service name {} group {} host {}",
                            clientDefinition.getServiceName(), clientDefinition.getGroup(), hostAndPort.toString());
                }
            }).sync();
            this.channel = channelFuture.channel();
            channel.closeFuture().addListener(future -> {
                rpcClient.remove(hostAndPort);
                if (future.isSuccess()) {
                    log.debug("Successfully to close RPC connection with service name {} group {} host {}",
                            clientDefinition.getServiceName(), clientDefinition.getGroup(), hostAndPort.toString());
                } else {
                    log.error("Fail to close RPC connection with service name {} group {} host {}",
                            clientDefinition.getServiceName(), clientDefinition.getGroup(), hostAndPort.toString());
                }
            });
        } catch (InterruptedException e) {
            log.error("Creating RPC connection exception", e);
            throw new TRpcSystemException(e);
        }

    }

    public RpcRequest send(RpcRequest rpcRequest) {
        RpcRequestsHolder.add(rpcRequest);
        this.channel.writeAndFlush(rpcRequest).addListener(future -> {
            if (!future.isSuccess()) {
                log.error("Fail on RpcRequest [{}] writeAndFlush", rpcRequest.getRequestId());
                rpcRequest.failOnDeliver();
            }
        });
        return rpcRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RpcConnection that = (RpcConnection) o;

        return hostAndPort.equals(that.hostAndPort);

    }

    @Override
    public int hashCode() {
        return hostAndPort.hashCode();
    }

    @Override
    public void close() {
        try {
            rpcClient.remove(hostAndPort);
            eventLoopGroup.shutdownGracefully();
            channel.close();
        } catch (Exception e) {
            log.error("RPC connection close exception", e);
        }
    }

}
