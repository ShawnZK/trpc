package com.sigmoid.arch.trpc.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ShawnZk on 2019/3/26.
 */
public class TRpcThreadFactory implements ThreadFactory {

    private static final String prefix = "trpc-thread-";

    private static final AtomicLong index = new AtomicLong(-1L);

    private String getName() {
        return prefix + index.incrementAndGet();
    }

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(false);
        thread.setName(getName());
        return thread;
    }

}
