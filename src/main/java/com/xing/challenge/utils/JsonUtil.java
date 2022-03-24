package com.xing.challenge.utils;

import com.google.gson.Gson;
import spark.ResponseTransformer;


public class JsonUtil {

    private static final Gson gson = new Gson();

    private JsonUtil() {

    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromStringToObject(String jsonString, Class<T> cls) {
        return gson.fromJson(jsonString, cls);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}

