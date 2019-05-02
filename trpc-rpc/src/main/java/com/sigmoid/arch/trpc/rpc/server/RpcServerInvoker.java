package com.sigmoid.arch.trpc.rpc.server;

import com.sigmoid.arch.trpc.rpc.model.RpcResponse;
import com.sigmoid.arch.trpc.rpc.model.RpcTask;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ShawnZk on 2019/4/3.
 */
@Slf4j
public class RpcServerInvoker {

    private ListeningExecutorService listeningExecutorService;

    private List<ListenableFuture> listenableFutures = Lists.newCopyOnWriteArrayList();

    private Lock lock = new ReentrantLock();

    private boolean closed = false;

    public RpcServerInvoker(ThreadPoolExecutor threadPoolExecutor) {
        this.listeningExecutorService = MoreExecutors.listeningDecorator(threadPoolExecutor);
    }

    public void submit(RpcTask rpcTask) {

        String requestId = rpcTask.getRequestId();
        Channel channel = rpcTask.getChannel();
        ListenableFuture<RpcResponse> listenableFuture = listeningExecutorService.submit(rpcTask);
        Futures.addCallback(listenableFuture, new FutureCallback<RpcResponse>() {
            @Override
            public void onSuccess(RpcResponse result) {
                log.debug("Success on request handle [{}]", requestId);
                channel.writeAndFlush(result).addListener(future -> {
                    listenableFutures.remove(listenableFuture);
                });
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("Fail on request handle [{}]", requestId);
            }
        }, listeningExecutorService);
        listenableFutures.add(listenableFuture);

    }

    //TODO where to call?
    public void close() {
        lock.lock();
        try {
            if (closed) {
                return;
            }
            listenableFutures.forEach(listenableFuture -> {
                while (!listenableFuture.isDone()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            listeningExecutorService.shutdown();
            closed = true;
        } finally {
            lock.unlock();
        }
    }

}
