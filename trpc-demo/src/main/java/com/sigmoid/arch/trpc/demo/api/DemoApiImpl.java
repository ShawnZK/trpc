package com.sigmoid.arch.trpc.demo.api;

/**
 * Created by z674095 on 2019/4/10.
 */
public class DemoApiImpl implements DemoApi {

    @Override
    public DemoResponse handle(DemoRequest demoRequest) {
        return new DemoResponse("a", 1);
    }

}
