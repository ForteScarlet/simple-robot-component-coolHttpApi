package com.forte.component.forcoolqhttpapi.beans.msg;

import com.forte.component.forcoolqhttpapi.beans.result.PluginStatus;
import com.forte.qqrobot.beans.messages.msgget.EventGet;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;

/**
 *
 * 元事件-心跳
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Heartbeat extends EventGet {

    /**
     * 插件状态
     */
    PluginStatus getStatus();

}
