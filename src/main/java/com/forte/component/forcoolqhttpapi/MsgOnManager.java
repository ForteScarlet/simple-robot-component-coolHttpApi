/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     MsgOnManager.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.beans.msg.BaseMsg;
import com.forte.component.forcoolqhttpapi.beans.msg.MsgOn;
import com.forte.component.forcoolqhttpapi.beans.msg.PostType;
import com.forte.component.forcoolqhttpapi.utils.PostTypeUtils;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.utils.AnnotationUtils;
import com.forte.qqrobot.utils.FieldUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 对于所有标注了@MsgOn的类统一管理
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class MsgOnManager {

    private static final Set<Class<? extends BaseMsg>> msgOnClasses = new HashSet<>();

    private static final AtomicReference<Map<PostType, Map<String, Class<? extends MsgGet>>>> postTypeMapCache = new AtomicReference<>(null);

    /**
     * 扫描指定包路径
     */
    public static void scan(String packageName) {
        // 扫描并获取所有的监听对象，然后转化
        new FileScanner()
                .find(
                        packageName,
                        c -> (AnnotationUtils.getAnnotation(c, MsgOn.class) != null) && FieldUtils.isChild(c, BaseMsg.class)
                ).get().stream().map(c -> (Class<? extends BaseMsg>) c).collect(Collectors.toSet()).forEach(MsgOnManager::register);

    }

    /**
     * 注册一个class
     */
    public static void register(Class<? extends BaseMsg> clazz) {
        Annotation annotation = AnnotationUtils.getAnnotation(clazz, MsgOn.class);
        if (annotation == null) {
            throw new NullPointerException("@MsgOn is null");
        }
        if (msgOnClasses.add(clazz)) {
            postTypeMapCache.set(null);
        }
    }

    public static Set<Class<? extends BaseMsg>> getAll() {
        return new HashSet<>(msgOnClasses);
    }

    /**
     * 直接获取postTypeMap，存在一层缓存，如果setClass没有变化则直接获取缓存值
     */
    public static Map<PostType, Map<String, Class<? extends MsgGet>>> getPostTypeMap() {
        return postTypeMapCache.updateAndGet(old -> old == null ? PostTypeUtils.toTypeMap(getAll()) : old);
    }

}
