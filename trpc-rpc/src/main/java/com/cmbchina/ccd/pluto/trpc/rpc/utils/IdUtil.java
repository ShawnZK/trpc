package com.cmbchina.ccd.pluto.trpc.rpc.utils;

import java.util.UUID;

/**
 * Created by z674095 on 2019/4/1.
 */
public class IdUtil {

    public static String genUuid() {
        return UUID.randomUUID().toString();
    }

}
