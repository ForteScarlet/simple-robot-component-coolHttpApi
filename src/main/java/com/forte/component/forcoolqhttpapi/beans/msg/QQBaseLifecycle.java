package com.forte.component.forcoolqhttpapi.beans.msg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * 元事件 - 生命周期
 * 暂时未想好如何进行整合
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.meta_event, messageType = QQBaseLifecycle.META_EVENT_TYPE)
public class QQBaseLifecycle extends BaseMsg {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	meta_event	上报类型
        meta_event_type	string	lifecycle	元事件类型
        sub_type	string	enable、disable	事件子类型，分别表示插件启用、插件停用
     */

    public static final PostType POST_TYPE = PostType.meta_event;
    public static final String META_EVENT_TYPE = "lifecycle";

    private LifecycleType sub_type;



    public static enum LifecycleType {
        enable,
        disable
    }
}
