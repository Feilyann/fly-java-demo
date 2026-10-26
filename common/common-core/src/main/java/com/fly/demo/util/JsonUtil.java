package com.fly.demo.util;

import com.alibaba.fastjson2.JSONObject;

/**
 * @project: creatar-common
 * @package: com.creatar.common.core.util
 * @author: feilongyan
 * @time: 2024/10/8 0008
 * @description: Json工具类
 * @version: 1.0
 */
public class JsonUtil {

    public static String toJson(Object o) {
        return JSONObject.toJSONString(o);
    }

    public static JSONObject parseJson(String json) {
        return JSONObject.parseObject(json);
    }

    public static <T> T parseJson(String json, Class<T> tClass) {
        return JSONObject.parseObject(json, tClass);
    }

    public static <T> T parseJsonObject(JSONObject jsonObject, Class<T> tClass) {
        return JSONObject.parseObject(jsonObject.toJSONString(), tClass);
    }
}
