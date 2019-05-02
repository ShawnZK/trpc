package com.sigmoid.arch.trpc.rpc.handler.tcp;

import com.sigmoid.arch.trpc.rpc.model.RpcRequest;
import com.sigmoid.arch.trpc.rpc.model.RpcTask;
import com.sigmoid.arch.trpc.rpc.server.RpcServerInvoker;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by z674095 on 2019/3/27.
 */
@Slf4j
@ChannelHandler.Sharable
public class TcpServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static TcpServerHandler instance = new TcpServerHandler();

    public static TcpServerHandler get() {
        return instance;
    }

    private static RpcServerInvoker rpcServerInvoker;

    public static void setInvoker(RpcServerInvoker invoker) {
        rpcServerInvoker = invoker;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        log.debug("TcpServerHandler channel [{}] receive requestId [{}] content [{}]",
                ctx.channel().id().asShortText(), rpcRequest.getRequestId(), rpcRequest);
        RpcTask rpcTask = new RpcTask(ctx.channel(), rpcRequest);
        rpcServerInvoker.submit(rpcTask);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("TcpServerHandler channel [{}] closed", ctx.channel().id().asShortText());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("TcpServerHandler channel [{}] connected", ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("TcpServerHandler channel [{}] exception", cause, ctx.channel().id().asShortText());
    }

}
