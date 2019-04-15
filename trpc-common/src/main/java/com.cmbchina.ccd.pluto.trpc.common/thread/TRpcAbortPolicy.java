package com.cmbchina.ccd.pluto.trpc.common.thread;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Z674095 on 2019/3/26.
 */
public class TRpcAbortPolicy extends ThreadPoolExecutor.AbortPolicy {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        String msg = String.format("TRpcServer["
                        + " Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d),"
                        + " Executor status: (isShutdown: %s, isTerminated: %s, isTerminating: %s)]",
                e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
        throw new RejectedExecutionException(msg);
    }

}
