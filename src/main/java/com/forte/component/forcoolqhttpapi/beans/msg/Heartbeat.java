/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     Heartbeat.java
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
import com.forte.qqrobot.beans.messages.msgget.EventGet;

/**
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
