package com.sigmoid.arch.trpc.demo.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

/**
 * Created by ShawnZk on 2019/4/10.
 */
@AllArgsConstructor
@Builder
@ToString
public class DemoResponse {

    private String code;

    private Integer count;

}
