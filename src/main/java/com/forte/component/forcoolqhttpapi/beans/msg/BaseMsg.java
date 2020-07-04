/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     BaseMsg.java
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

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 全部监听消息的父类
 * <p>
 * 实现{@link MsgGet} 接口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public abstract class BaseMsg implements MsgGet {
    /*

    其它字段随上报类型不同而有所不同，下面将在事件列表的「上报数据」标题下一一给出。

    某些字段的值是一些固定的值，在表格的「可能的值」中给出，如果「可能的值」为空则表示没有固定的可能性。

    另外，每一次上报都有下面几个字段，后面不再列出。

    time	number (int64)	事件发生的时间戳
    self_id	number (int64)	收到消息的机器人 QQ 号

    关于上面的 time 字段，由于 酷Q 的某次更新中移除了消息事件的 time 参数，
    因此目前插件上报的数据中，notice 和 request 类型上报的 time 是 酷Q 原生给出的时间，
    而 message 类型的上报中的 time 是事件到达插件的时间，后者有可能和事件实际的发生时间有差别。

     */

    private long time;
    private String self_id;

    /**
     * 从此定义原始数据字段
     */
    private String originalData;


    /**
     * 获取ID, 默认ID就是当前对象的hashcode
     */
    @Override
    public String getId() {
        return self_id + "@" + Integer.toHexString(hashCode());
    }

    /**
     * 此消息获取的时候，代表的是哪个账号获取到的消息。
     *
     * @return 接收到此消息的账号。
     */
    @Override
    public String getThisCode() {
        return self_id;
    }

    /**
     * 允许重新定义Code以实现在存在多个机器人的时候切换处理。
     *
     * @param code code
     */
    @Override
    public void setThisCode(String code) {
        self_id = code;
    }


    /**
     * 获取到的时间, 代表某一时间的毫秒值或者秒值。此处默认为此对象被创建的时候的毫秒值
     */
    @Override
    public Long getTime() {
        return time;
    }
}
