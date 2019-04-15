package com.cmbchina.ccd.pluto.trpc.rpc.client;

import com.cmbchina.ccd.pluto.trpc.common.definition.ClientDefinition;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by z674095 on 2019/4/2.
 */
@Builder
@ToString
public class ClientBean {

    @Getter
    private String group;

    @Getter
    private String serviceName;

    @Getter
    private String ifaceName;

    public static List<ClientBean> buildFromClientDefinition(ClientDefinition clientDefinition) {
        return clientDefinition.getIfaceNames().stream()
                .map(i -> ClientBean.builder()
                        .serviceName(clientDefinition.getServiceName())
                        .group(clientDefinition.getGroup())
                        .ifaceName(i).build())
                .collect(toList());
    }

}
