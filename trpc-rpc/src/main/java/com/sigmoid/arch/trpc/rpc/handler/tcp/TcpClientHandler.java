package com.sigmoid.arch.trpc.rpc.handler.tcp;

import com.sigmoid.arch.trpc.rpc.model.RpcResponse;
import com.google.common.net.HostAndPort;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.sigmoid.arch.trpc.rpc.model.RpcRequestsHolder.getById;

/**
 *
 * Created by ShawnZk on 2019/3/28.
 */
@Slf4j
@ChannelHandler.Sharable
public class TcpClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private static TcpClientHandler instance = new TcpClientHandler();

    public static TcpClientHandler get() {
        return instance;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        String requestId = rpcResponse.getRequestId();
        if (rpcResponse == null || rpcResponse.getRequestId() == null) {
            log.error("Channel [{}] read response with empty requestId", channelId);
            return;
        }
        log.debug("Channel [{}] read request [{}]", channelId, requestId);
        Optional.ofNullable(getById(requestId)).ifPresent((rpcRequest) -> rpcRequest.updateOnReply(rpcResponse));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        char[] addrChars = ctx.channel().remoteAddress().toString().toCharArray();
        HostAndPort.fromString(String.valueOf(addrChars, 1, addrChars.length-1));
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

}
