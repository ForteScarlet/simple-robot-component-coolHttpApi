/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     GetCanSendImage.java
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

import com.forte.component.forcoolqhttpapi.beans.result.CanSendImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 检查是否可以发送图片
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class GetCanSendImage implements Get<CanSendImage> {

    public static final String API = "/can_send_image";

    @Override
    public String getApi() {
        return API;
    }

    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<CanSendImage> getResultType() {
        return CanSendImage.class;
    }
}
