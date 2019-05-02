package com.sigmoid.arch.trpc.rpc.model;

import com.sigmoid.arch.trpc.common.enums.ExceptionTypeEnum;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by ShawnZk on 2019/3/26.
 */
@Builder
public class RpcResponse implements Serializable {

    @Getter
    private String requestId;

    @Getter
    private ExceptionTypeEnum exceptionTypeEnum;

    @Getter
    private String exceptionMsg;

    @Getter
    private String resultJson;

}
