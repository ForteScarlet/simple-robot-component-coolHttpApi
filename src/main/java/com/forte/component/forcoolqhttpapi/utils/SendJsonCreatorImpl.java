package com.forte.component.forcoolqhttpapi.utils;

import com.alibaba.fastjson.JSON;
import com.forte.component.forcoolqhttpapi.beans.send.*;
import com.forte.component.forcoolqhttpapi.beans.set.Set;
import com.forte.component.forcoolqhttpapi.beans.set.SetGroupKick;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 *  创建对应的消息类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class SendJsonCreatorImpl {

    /**
     * 发送私信消息
     * @see #sendPrivateMsg(long, String, boolean)
     */
    public static Send sendPrivateMsg(long QQ, String msg){
        return sendPrivateMsg(QQ, msg, false);
    }

    /**
     * 发送私信消息
     * @param QQ    QQ号
     * @param msg   信息内容
     * @param autoEscape 自动转码
     */
    public static Send sendPrivateMsg(long QQ, String msg, boolean autoEscape){
        return new SendPrivateMsg(QQ, msg, autoEscape);
    }

    /**
     * 发送群消息
     * @see #sendgroupMsg(long, String, boolean)
     */
    public static Send sendGroupMsg(long group, String msg){
        return new SendGroupMsg(group, msg, false);
    }

    /**
     * 发送群消息
     * @param group QQ号
     * @param msg   信息内容
     * @param autoEscape    自动转码
     */
    public static Send sendgroupMsg(long group, String msg, boolean autoEscape){
        return new SendGroupMsg(group, msg, autoEscape);
    }

    /**
     * 发送讨论组消息
     * @see #sendDiscussMsg(long, String, boolean)
     */
    public static Send sendDiscussMsg(long group, String msg){
        return sendDiscussMsg(group, msg, false);
    }

    /**
     * 发送讨论组消息
     * @param group 讨论组号
     * @param msg   消息
     * @param autoEscape    自动转码
     */
    public static Send sendDiscussMsg(long group, String msg, boolean autoEscape){
        return new SendDiscussMsg(group, msg, autoEscape);
    }

    /**
     * 消息撤回
     * @param id    消息的id
     */
    public static Send sendDeleteMsg(String id){
        return new SendDeleteMsg(id);
    }

    /**
     * 发送赞
     * @param QQ        qq号
     * @param times     赞的次数，每人每天最多10次
     */
    public static Send sendLike(long QQ, int times){
        return new SendLike(QQ, times);
    }

    /**
     * 发送赞
     * @see #sendLike(long, int)
     */
    public static Send sendLike(long QQ){
        return new SendLike(QQ, 1);
    }

    /**
     * 群踢人
     * @param group     群号
     * @param QQ        qq号
     * @param dontBack  不允许再回来
     * @return
     */
    public static Set setGroupKick(long group, long QQ, boolean dontBack){
        return new SetGroupKick(group, QQ, dontBack);
    }

    /**
     * 群踢人
     * @see #setGroupKick(long, long, boolean)
     */
    public static Set setGroupKick(long group, long QQ){
        return setGroupKick(group, QQ, false);
    }

















    /**
     * 直接转化为JSON字符串
     */
    private static String toJSON(Object[]... args){
        return JSON.toJSONString(toJSONMap(args));
    }

    /**
     * 创建JSON对对应的Map
     * @param args 每一个Object数组必须保证有2个值
     * @return
     */
    public static Map<String, Object> toJSONMap(Object[]... args){
        return new LinkedHashMap<String, Object>(16){{
            for (Object[] o : args) {
                put(String.valueOf(o[0]), o[1]);
            }
        }};
    }

}
