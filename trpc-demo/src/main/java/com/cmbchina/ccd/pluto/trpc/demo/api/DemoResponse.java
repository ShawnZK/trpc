package com.cmbchina.ccd.pluto.trpc.demo.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

/**
 * Created by z674095 on 2019/4/10.
 */
@AllArgsConstructor
@Builder
@ToString
public class DemoResponse {

    private String code;

    private Integer count;

}