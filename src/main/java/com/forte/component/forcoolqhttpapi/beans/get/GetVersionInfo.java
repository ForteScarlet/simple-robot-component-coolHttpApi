/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     GetVersionInfo.java
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

import com.forte.component.forcoolqhttpapi.beans.result.VersionInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 获取 酷Q 及 HTTP API 插件的版本信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class GetVersionInfo implements Get<VersionInfo> {

    public static final GetVersionInfo INSTANCE = new GetVersionInfo();

    public static final String API = "/get_version_info";

    @Override
    public String getApi() {
        return API;
    }


    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<VersionInfo> getResultType() {
        return VersionInfo.class;
    }
}
