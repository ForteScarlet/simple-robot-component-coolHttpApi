package com.forte.component.forcoolqhttpapi.beans.msg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * 元事件 - 生命周期
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.meta_event, messageType = QQBaseLifecycle.META_EVENT_TYPE)
public class QQBaseLifecycle extends BaseMsg implements Lifecycle {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	meta_event	上报类型
        meta_event_type	string	lifecycle	元事件类型
        sub_type	string	enable、disable	事件子类型，分别表示插件启用、插件停用
     */

    public static final PostType POST_TYPE = PostType.meta_event;
    public static final String META_EVENT_TYPE = "lifecycle";

    private LifecycleType sub_type;

    @Override
    public LifecycleType getSubType() {
        return sub_type;
    }

    /**
     * 一般来讲，监听到的消息大部分都会有个“消息内容”。定义此方法获取消息内容。
     * 如果不存在，则为null。（旧版本推荐为空字符串，现在不了。我变卦了）
     */
    @Override
    public String getMsg() {
        return sub_type.name();
    }

    /**
     * 重新设置消息
     *
     * @param newMsg msg
     * @since 1.7.x
     */
    @Override
    public void setMsg(String newMsg) { }

    /**
     * 获取消息的字体
     */
    @Override
    public String getFont() {
        return null;
    }


    public static enum LifecycleType {
        enable,
        disable,
        /**
         * @since 1.7.x , http api v 4.14.0
         */
        connect
        ;
    }
}
