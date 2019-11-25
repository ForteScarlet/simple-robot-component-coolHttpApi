package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.beans.msg.Heartbeat;
import com.forte.component.forcoolqhttpapi.beans.msg.Lifecycle;
import com.forte.qqrobot.anno.factory.MsgGetTypeFactory;

/**
 *
 * 算是个常量类，提供额外的两个扩展MsgGet类型
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ExMsgGet {
    /** 元事件 - 生命周期 */
    public static final String LIFECYCLE = "lifecycle";
    /** 元事件 - 心跳 */
    public static final String HEARTBEAT = "heartbeat";

}
