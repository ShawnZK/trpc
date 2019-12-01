package com.sigmoid.arch.trpc.rpc.model;

import com.sigmoid.arch.trpc.common.enums.ExceptionTypeEnum;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by ShawnZk on 2019/3/26.
 */
@Builder
@Getter
public class RpcResponse implements Serializable {

    private String requestId;

    private ExceptionTypeEnum exceptionTypeEnum;

    private String exceptionMsg;

    private String[] exceptionStacks;

    private String resultJson;

}
