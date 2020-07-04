/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     Get.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.beans.get;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.forte.component.forcoolqhttpapi.beans.result.Result;
import com.forte.component.forcoolqhttpapi.beans.send.Send;

/**
 * 获取相关的接口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Get<R extends Result> extends Send {

    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @JSONField(serialize = false)
    Class<R> getResultType();

    /**
     * toJSON字符串
     */
    default String toJSON() {
        return JSON.toJSONString(this);
    }

}
