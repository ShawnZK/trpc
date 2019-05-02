package com.sigmoid.arch.trpc.rpc.utils;

import static com.sigmoid.arch.trpc.common.constants.SymbolConstants.Mapping_Key_Connection_Symbol;

/**
 * Created by ShawnZk on 2019/4/1.
 */
public class RpcUtil {

    public static String genClientDefinitionMappingKey(String serviceName, String group) {
        StringBuilder sb = new StringBuilder();
        sb.append(serviceName);
        sb.append(Mapping_Key_Connection_Symbol);
        sb.append(group);
        sb.append(Mapping_Key_Connection_Symbol);
        return sb.toString();
    }

    public static String genClientProxyMappingKey(String serviceName, String group, String ifaceName) {
        StringBuilder sb = new StringBuilder();
        sb.append(serviceName);
        sb.append(Mapping_Key_Connection_Symbol);
        sb.append(group);
        sb.append(Mapping_Key_Connection_Symbol);
        sb.append(ifaceName);
        sb.append(Mapping_Key_Connection_Symbol);
        return sb.toString();
    }

    public static String genServerBeanMappingKey(String ifaceName) {
        StringBuilder sb = new StringBuilder();
        sb.append(ifaceName);
        sb.append(Mapping_Key_Connection_Symbol);
        return sb.toString();
    }

    public static String genServerMethodMappingKey(String clazzName, String methodName, String[] argTypeNames) {
        StringBuilder sb = new StringBuilder();
        sb.append(clazzName);
        sb.append(Mapping_Key_Connection_Symbol);
        sb.append(methodName);
        sb.append(Mapping_Key_Connection_Symbol);
        for (String argTypeName : argTypeNames) {
            sb.append(argTypeName);
            sb.append(Mapping_Key_Connection_Symbol);
        }
        return sb.toString();
    }

    public static String genServerMethodMappingKey(String clazzName, String methodName, Class<?>[] argTypes) {
        StringBuilder sb = new StringBuilder();
        sb.append(clazzName);
        sb.append(Mapping_Key_Connection_Symbol);
        sb.append(methodName);
        sb.append(Mapping_Key_Connection_Symbol);
        for (Class<?> argType : argTypes) {
            sb.append(argType.getName());
            sb.append(Mapping_Key_Connection_Symbol);
        }
        return sb.toString();
    }

}
