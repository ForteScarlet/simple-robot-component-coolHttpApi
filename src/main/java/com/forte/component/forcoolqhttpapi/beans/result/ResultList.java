/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     ResultList.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.beans.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 返回值为列表的时候，通过实例化一个列表对象并将数组对象封装进去
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ResultList<R extends ResultInner> extends Result {

    /**
     * 获取数组类型
     */
    @JSONField(serialize = false)
    Class<R> getListType();

}
