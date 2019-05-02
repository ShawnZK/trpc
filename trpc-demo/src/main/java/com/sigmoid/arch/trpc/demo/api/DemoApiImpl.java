package com.sigmoid.arch.trpc.demo.api;

/**
 * Created by ShawnZk on 2019/4/10.
 */
public class DemoApiImpl implements DemoApi {

    @Override
    public DemoResponse handle(DemoRequest demoRequest) {
        return new DemoResponse("a", 1);
    }

}
