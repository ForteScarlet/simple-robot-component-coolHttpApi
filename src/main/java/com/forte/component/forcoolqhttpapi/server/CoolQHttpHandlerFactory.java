package com.forte.component.forcoolqhttpapi.server;

import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.utils.JSONDataUtil;
import com.forte.lang.Language;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.log.QQLogLang;
import com.forte.qqrobot.sender.senderlist.RootSenderList;
import com.forte.qqrobot.sender.senderlist.SenderList;
import com.forte.qqrobot.utils.ObjectsPlus;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.IOUtils;

import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

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

    /** factory中使用的log日志 */
    protected static final QQLogLang LOG_LANG = new QQLogLang("cq.httpapi.handler.factory");
    protected QQLogLang getLog(){
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
