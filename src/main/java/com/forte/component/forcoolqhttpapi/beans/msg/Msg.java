package com.forte.component.forcoolqhttpapi.beans.msg;

import com.forte.qqrobot.beans.messages.TimeAble;

/**
 * 接收到的消息的接口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Deprecated
public interface Msg extends TimeAble {

    /*
        self_id	number (int64)	收到消息的机器人 QQ 号
     */
    // 每次上报的数据中必有的一个字段是 post_type，数据类型为字符串，用来指示此次上报的类型

    /**
     * 获取上报格式
     */
    PostType getPostType();

    /**
     * 收到消息的机器人QQ号 -> self_id
     */
    String getSelfQQ();

    /*
        time	number (int64)	事件发生的时间戳
        self_id	number (int64)	收到消息的机器人 QQ 号


        关于上面的 time 字段，由于 酷Q 的某次更新中移除了消息事件的 time 参数，
        因此目前插件上报的数据中，notice 和 request 类型上报的 time 是 酷Q 原生给出的时间，
        而 message 类型的上报中的 time 是事件到达插件的时间，后者有可能和事件实际的发生时间有差别。
     */

}
