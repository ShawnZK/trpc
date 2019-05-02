package com.sigmoid.arch.trpc.common.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by ShawnZk on 2019/3/26.
 */
@AllArgsConstructor
@NoArgsConstructor
public class EnvDefinition {

    @Getter
    private String registryType;

    @Getter
    private String addrs;

}
