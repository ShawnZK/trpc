package com.cmbchina.ccd.pluto.trpc.rpc.utils;

/**
 * Created by Z674095 on 2019/3/26.
 */
public class PlatformUtil {

    public static boolean isOnLinux() {
        return System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0;
    }

}
