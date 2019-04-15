package com.cmbchina.ccd.pluto.trpc.common.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Z674095 on 2019/3/26.
 */
@AllArgsConstructor
@NoArgsConstructor
public class EnvDefinition {

    @Getter
    private String registryType;

    @Getter
    private String addrs;

}
