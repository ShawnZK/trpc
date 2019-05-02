package com.sigmoid.arch.trpc.rpc.utils;

/**
 * Created by ShawnZk on 2019/3/26.
 */
public class PlatformUtil {

    public static boolean isOnLinux() {
        return System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0;
    }

}
