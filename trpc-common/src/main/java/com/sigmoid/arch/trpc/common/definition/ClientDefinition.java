package com.sigmoid.arch.trpc.common.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Created by Z674095 on 2019/3/26.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ClientDefinition {

    @Getter
    private String group;

    @Getter
    private String serviceName;

    @Getter
    private Set<String> ifaceNames;

    @Getter
    private int timeoutInMillis;

    @Getter
    private String transport;

    @Getter
    private String codes;

    @Getter
    private Set<String> providers;

}
