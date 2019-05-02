package com.sigmoid.arch.trpc.common.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by Z674095 on 2019/3/26.
 */
public class TRpcThreadPool extends ThreadPoolExecutor {

    public TRpcThreadPool(int poolSize, int queueSize) {
        super(poolSize,
                poolSize,
                0L,
                TimeUnit.MILLISECONDS,
                queueSize == 0 ? new SynchronousQueue<Runnable>() : queueSize < 0 ?
                        new LinkedBlockingQueue<Runnable>() : new LinkedBlockingQueue<Runnable>(queueSize),
                new TRpcThreadFactory(),
                new TRpcAbortPolicy());
    }

}
