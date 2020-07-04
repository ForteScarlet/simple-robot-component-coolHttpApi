/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     VersionInfo.java
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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 酷Q 及 HTTP API 插件的版本信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class VersionInfo implements Result {
    /*
        响应数据
        字段名	数据类型	说明
        coolq_directory	string	酷Q 根目录路径
        coolq_edition	string	酷Q 版本，air 或 pro
        plugin_version	string	HTTP API 插件版本，例如 2.1.3
        plugin_build_number	number	HTTP API 插件 build 号
        plugin_build_configuration	string	HTTP API 插件编译配置，debug 或 release
     */
    private String coolq_directory;
    private String coolq_edition;
    private String plugin_version;
    private String plugin_build_number;
    private String plugin_build_configuration;
    private String originalData;

}
