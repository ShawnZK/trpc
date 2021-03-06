package com.sigmoid.arch.trpc.rpc.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ShawnZk on 2019/3/27.
 */
public class JsonSerializer implements RpcSerializer {

    //Gson is thread safe
    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .disableHtmlEscaping()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();

    @Override
    public byte[] serialize(Object obj) {
        return gson.toJson(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return gson.fromJson(new String(bytes), clazz);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

}
