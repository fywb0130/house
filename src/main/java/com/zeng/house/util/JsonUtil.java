package com.zeng.house.util;

import com.google.gson.Gson;

/**
 * Created by zengqiang on 2017/8/15.
 */
public class JsonUtil {
    private static Gson gson = new Gson();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }
}
