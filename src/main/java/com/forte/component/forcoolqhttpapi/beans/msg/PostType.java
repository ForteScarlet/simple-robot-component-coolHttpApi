/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     PostType.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

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
     * 收到消息
     */
    message("message"/*, "message_type"*/),
    /**
     * 群、讨论组变动等通知类事件
     */
    notice("notice"/*, "notice_type"*/),
    /**
     * 加好友请求、加群请求／邀请
     */
    request("request"/*, "request_type"*/),

    /**
     * 元事件
     */
    meta_event("meta_event"/*, "meta_event_type"*/),

    ;

    public final String keyName;
    public final String postType;

    PostType(String postType, String keyName) {
        this.postType = postType;
        this.keyName = keyName;
    }

    PostType(String postType) {
        this.postType = postType;
        this.keyName = postType + "_type";
    }

}
