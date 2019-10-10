package com.forte.component.forcoolqhttpapi.beans.msg;

/**
 * 上报类型
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum PostType {
     /*
    上报数据格式
    每次上报的数据中必有的一个字段是 post_type，数据类型为字符串，用来指示此次上报的类型，有如下三种：
    上报类型	说明
    message	收到消息
    notice	群、讨论组变动等通知类事件
    request	加好友请求、加群请求／邀请
     */

    /**
     *  收到消息
     */
    message,
    /**
     *  群、讨论组变动等通知类事件
     */
    notice,
    /**
     *  加好友请求、加群请求／邀请
     */
    request

    ;

}
