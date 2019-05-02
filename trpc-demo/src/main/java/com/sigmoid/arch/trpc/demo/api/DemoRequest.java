package com.sigmoid.arch.trpc.demo.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Created by z674095 on 2019/4/9.
 */
@AllArgsConstructor
@ToString
public class DemoRequest {

    @Getter
    private Integer a;

    @Getter
    private String b;

    @Getter
    private Boolean c;

    @Getter
    private List<Inner> inners;

    @AllArgsConstructor
    public static class Inner {
        private String e;
        private Long f;
    }

}
