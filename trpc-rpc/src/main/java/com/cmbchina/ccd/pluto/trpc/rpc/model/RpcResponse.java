package com.cmbchina.ccd.pluto.trpc.rpc.model;

import com.cmbchina.ccd.pluto.trpc.common.enums.ExceptionTypeEnum;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Z674095 on 2019/3/26.
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
