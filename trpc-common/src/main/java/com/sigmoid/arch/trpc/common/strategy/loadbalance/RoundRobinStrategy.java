package com.sigmoid.arch.trpc.common.strategy.loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ShawnZk on 2019/3/29.
 */
public class RoundRobinStrategy implements LbStrategy {

    private AtomicLong index = new AtomicLong(-1L);

    public <T> T getResource(List<T> resources) {
        long l = index.incrementAndGet();
        int size = resources.size();
        int i = (int) (l % size);
        if (i < 0) {
            i += size;
        }
        return resources.get(i);
    }

}
