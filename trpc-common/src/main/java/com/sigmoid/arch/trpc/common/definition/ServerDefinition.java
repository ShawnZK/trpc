package com.sigmoid.arch.trpc.common.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Created by ShawnZk on 2019/3/26.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerDefinition {

    private String group;

    private String serviceName;

    private Set<String> exposeIfaceNames;

    private int threadPoolSize;

    private int threadQueueSize;

    private int port;

    private String transport;

    private String codec;

    private int timeoutInMillis;

}
