package com.sigmoid.arch.trpc.rpc.utils;

import java.util.UUID;

/**
 * Created by ShawnZk on 2019/4/1.
 */
public class IdUtil {

    public static String genUuid() {
        return UUID.randomUUID().toString();
    }

}
