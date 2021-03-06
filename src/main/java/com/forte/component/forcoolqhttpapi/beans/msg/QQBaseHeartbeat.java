/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     QQBaseHeartbeat.java
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

import com.forte.component.forcoolqhttpapi.beans.result.PluginStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 元事件 - 心跳
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.meta_event, messageType = QQBaseHeartbeat.META_EVENT_TYPE)
public class QQBaseHeartbeat extends BaseMsg implements Heartbeat {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	meta_event	上报类型
        meta_event_type	string	heartbeat	元事件类型
        status	object	-	状态信息
     */

    public static final PostType POST_TYPE = PostType.meta_event;
    public static final String META_EVENT_TYPE = "heartbeat";

    /**
     * 同 get_status 返回值
     */
    private PluginStatus status;


    /**
     * 重新设置消息
     *
     * @param newMsg msg
     * @since 1.7.x
     */
    @Override
    public void setMsg(String newMsg) {
    }
}
