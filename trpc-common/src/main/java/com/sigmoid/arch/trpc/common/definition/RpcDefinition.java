package com.sigmoid.arch.trpc.common.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by ShawnZk on 2019/3/26.
 */
@AllArgsConstructor
@NoArgsConstructor
public class RpcDefinition {

    @Getter
    private EnvDefinition envDefinition;

    @Getter
    private ServerDefinition serverDefinition;

    @Getter
    private List<ClientDefinition> clientDefinitions;

}
