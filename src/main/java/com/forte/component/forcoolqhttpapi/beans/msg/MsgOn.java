package com.forte.component.forcoolqhttpapi.beans.msg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于内部包扫描使用
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Target(ElementType.TYPE) //接口、类、枚举、注解
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgOn {

    /**
     * 上报类型
     */
    PostType type();

    /**
     * 详细消息类型
     */
    String messageType();

}
