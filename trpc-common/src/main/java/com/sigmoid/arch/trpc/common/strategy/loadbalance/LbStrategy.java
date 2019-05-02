package com.sigmoid.arch.trpc.common.strategy.loadbalance;

import java.util.List;

/**
 * Created by ShawnZk on 2019/3/29.
 */
public interface LbStrategy {

    <T> T getResource(List<T> resources);

}
