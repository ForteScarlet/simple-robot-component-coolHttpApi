package com.forte.component.forcoolqhttpapi.beans.msg;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;

/**
 *
 * 元事件 - 生命周期
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Lifecycle extends MsgGet {

    /**
     * enable、disable	事件子类型，分别表示插件启用、插件停用
     */
    QQBaseLifecycle.LifecycleType getSubType();

}
