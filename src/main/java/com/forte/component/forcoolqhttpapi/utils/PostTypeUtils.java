/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     PostTypeUtils.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.utils;

import com.forte.component.forcoolqhttpapi.beans.msg.BaseMsg;
import com.forte.component.forcoolqhttpapi.beans.msg.MsgOn;
import com.forte.component.forcoolqhttpapi.beans.msg.PostType;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.utils.reflect.FieldUtils;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class PostTypeUtils {

    /**
     * 获取到扫描到的全部Class对象，转化为对应格式的Map
     *
     * @param collections
     * @return
     */
    public static Map<PostType, Map<String, Class<? extends MsgGet>>> toTypeMap(Collection<Class<? extends BaseMsg>> collections) {
        // 过滤转化
        return collections.stream()
                .filter(c -> c.getAnnotation(MsgOn.class) != null)
                .filter(c -> FieldUtils.isChild(c, MsgGet.class))
                .collect(Collectors.groupingBy(
                        c -> c.getAnnotation(MsgOn.class).type(),
                        Collectors.toMap(
                                c -> c.getAnnotation(MsgOn.class).messageType(),
                                c -> (Class<? extends MsgGet>) c
                        )

                ));
    }

}
