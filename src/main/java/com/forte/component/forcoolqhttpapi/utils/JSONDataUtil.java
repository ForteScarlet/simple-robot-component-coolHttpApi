package com.forte.component.forcoolqhttpapi.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 为Json数据添加originalData数据
 * 目前使用的是fastJson包
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class JSONDataUtil {
    public static final String KEY_NAME = "originalData";

    /**
     * 将字符串转化为JSON对象
     *
     * @param jsonStr json字符串
     * @return JSON对象
     */
    public static JSONObject toJSONObject(String jsonStr) {
        return putObjOriginal(JSON.parseObject(jsonStr));
    }

    /**
     * 为JSON对象添加original字段（如果可以添加的话
     *
     * @param json JSON对象
     */
    public static JSON putOriginal(JSON json) {
        // 判断类型
        if (json instanceof JSONObject) {
            return putObjOriginal((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return putArrayOriginal((JSONArray) json);
        } else {
            // 如果两个类型都不是，不做处理直接返回
            return json;
        }
    }


    /**
     * 为Object类型添加字段
     *
     * @param jobj JSONObject对象
     */
    public static JSONObject putObjOriginal(JSONObject jobj) {
        // 如果是Object类型，添加一个字段
        jobj.put(KEY_NAME, toJSONString(jobj));
        return jobj;
    }

    /**
     * 为array类型添加字段
     *
     * @param array JSONArray对象
     */
    public static JSONArray putArrayOriginal(JSONArray array) {
        // 遍历JSONArray对象
        array.forEach(o -> {
            if (o instanceof JSON) {
                putOriginal((JSON) o);
            }
        });
        // 虽然返回值可能没啥意义
        return array;
    }


    /**
     * 将对象转化为字符串
     *
     * @param obj 对象
     */
    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }
}
