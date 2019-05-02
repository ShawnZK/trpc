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
public class ServerDefinition {

    @Getter
    private String group;

    @Getter
    private String serviceName;

    @Getter
    private Set<String> exposeIfaceNames;

    @Getter
    private int threadPoolSize;

    @Getter
    private int threadQueueSize;

    @Getter
    private int port;

    @Getter
    private String transport;

    @Getter
    private String codec;

}
