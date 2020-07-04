/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     CoolQHttpHandlerFactory.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.server;

import com.forte.qqrobot.log.QQLogLang;

import java.util.HashMap;
import java.util.Map;

/**
 * http监听服务所监听的服务工厂类
 * <p>
 * 一般需要监听的消息就是那些监听消息类型
 * 可以尝试直接通过包扫描进行创建
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpHandlerFactory {

    /**
     * factory中使用的log日志
     */
    protected static final QQLogLang LOG_LANG = new QQLogLang("cq.httpapi.handler.factory");

    protected QQLogLang getLog() {
        return LOG_LANG;
    }

    /**
     * post_type上报类型的key值
     */
    public static final String POST_TYPE_KEY = "post_type";
    // message、notice、request
    /*
        以下为post_type参数可能的值
     */

    public static final String POST_TYPE_MESSAGE = "message";
    public static final String POST_TYPE_NOTICE = "notice";
    public static final String POST_TYPE_REQUEST = "request";

    /*
        以下为三种上报类型相对应的数据类型的值的key值
     */

    public static final String MSG_TYPE_KEY_MESSAGE = "message_type";
    public static final String MSG_TYPE_KEY_NOTICE = "notice_type";
    public static final String MSG_TYPE_KEY_REQUEST = "request_type";

    /**
     * posttype与对应类型的key的对应关系
     */
    private static final Map<String, String> POST_TPYE_FOR_MSG_TYPE_KEY = new HashMap<String, String>(4) {{
        put(POST_TYPE_MESSAGE, MSG_TYPE_KEY_MESSAGE);
        put(POST_TYPE_NOTICE, MSG_TYPE_KEY_NOTICE);
        put(POST_TYPE_REQUEST, MSG_TYPE_KEY_REQUEST);
    }};

    // 到时候直接扫描这个包就行了

    public static final String MSG_GET_PACK = "com.forte.component.forcoolqhttpapi.beans.msg";

}
