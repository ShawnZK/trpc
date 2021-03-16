package com.sigmoid.arch.trpc.rpc.server;


import com.google.common.net.HostAndPort;
import com.sigmoid.arch.trpc.common.definition.ServerDefinition;
import com.sigmoid.arch.trpc.common.thread.TRpcThreadPool;
import com.sigmoid.arch.trpc.registry.RegistryHandler;
import com.sigmoid.arch.trpc.registry.RegistryTypes;
import com.sigmoid.arch.trpc.registry.event.ServerRegistryEvent;
import com.sigmoid.arch.trpc.rpc.handler.tcp.TcpServerHandler;
import com.sigmoid.arch.trpc.rpc.selector.SerializerSelector;
import com.sigmoid.arch.trpc.rpc.selector.TransportSelector;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.apache.commons.lang3.SystemUtils.IS_OS_LINUX;


/**
 *
 * Created by ShawnZk on 2019/3/26.
 */
@Slf4j
public class RpcServer {

    private ServerBootstrap serverBootstrap;

    private RegistryHandler registryHandler;

    private HostAndPort hostAndPort;

    private String group;

    private String serviceName;

    private int port;

    private int poolSize;

    private int queueSize;

    private String transport;

    private String codec;

    private int timeoutInMillis;

    private void initOptions() {
        //TODO
    }

    private void initHandlers() {
        ChannelInitializer channelInitializer = TransportSelector
                .selectServerChannelInitializer(transport, SerializerSelector.select(codec));
        serverBootstrap.childHandler(channelInitializer);
    }

    private void initThreadPool() {
        TcpServerHandler.setInvoker(new RpcServerInvoker(new TRpcThreadPool(poolSize, queueSize)));
    }

    public void start() {

        serverBootstrap = new ServerBootstrap();

        EventLoopGroup bossGroup = IS_OS_LINUX ? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = IS_OS_LINUX ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(IS_OS_LINUX ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
        initThreadPool();
        initOptions();
        initHandlers();

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync()
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            log.debug("RpcServer start success");
                            registryHandler.pub(
                                    new ServerRegistryEvent(RegistryTypes.SERVER_ONLINE, group, serviceName, hostAndPort));
                        } else {
                            log.debug("RpcServer start fail", future.cause());
                            registryHandler.pub(
                                    new ServerRegistryEvent(RegistryTypes.SERVER_OFFLINE, group, serviceName, hostAndPort));
                        }
                    });
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("RpcServer start error", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public RpcServer(ServerDefinition serverDefinition, RegistryHandler registryHandler) throws UnknownHostException {
        this.group = serverDefinition.getGroup();
        this.serviceName = serverDefinition.getServiceName();
        this.port = serverDefinition.getPort();
        this.poolSize = serverDefinition.getThreadPoolSize();
        this.queueSize = serverDefinition.getThreadQueueSize();
        this.transport = serverDefinition.getTransport();
        this.codec = serverDefinition.getCodec();
        this.hostAndPort = HostAndPort.fromParts(InetAddress.getLocalHost().getHostAddress(), this.port);
        this.registryHandler = registryHandler;
    }

}
