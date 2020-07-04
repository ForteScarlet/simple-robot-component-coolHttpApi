/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     GetCookies.java
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

import com.forte.component.forcoolqhttpapi.beans.result.QQCookies;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 获取Cookie信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@NoArgsConstructor
public class GetCookies implements Get<QQCookies> {

    public static final String API = "/get_cookies";

    @Override
    public String getApi() {
        return API;
    }

    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<QQCookies> getResultType() {
        return QQCookies.class;
    }

}
