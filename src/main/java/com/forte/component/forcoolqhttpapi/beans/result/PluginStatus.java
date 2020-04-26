package com.forte.component.forcoolqhttpapi.beans.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 插件运行状态
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class PluginStatus implements Result {
    /*
    响应数据
        字段名	数据类型	说明
        app_initialized	boolean	HTTP API 插件已初始化
        app_enabled	boolean	HTTP API 插件已启用
        plugins_good	object	HTTP API 的各内部插件是否正常运行
        app_good	boolean	HTTP API 插件正常运行（已初始化、已启用、各内部插件正常运行）
        online	boolean	当前 QQ 在线，null 表示无法查询到在线状态
        good	boolean	HTTP API 插件状态符合预期，意味着插件已初始化，内部插件都在正常运行，且 QQ 在线
     */
    private boolean app_initialized;
    private boolean app_enabled;
    private Object plugins_good;
    private boolean app_good;
    private boolean online;
    private boolean good;
    private String originalData;


}
