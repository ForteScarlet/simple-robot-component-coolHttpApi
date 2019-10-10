package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 重启 HTTP API 插件
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetRestartPlugin implements Set {
    /*
        参数
        字段名	数据类型	默认值	说明
        delay	number	0	要延迟的毫秒数，如果默认情况下无法重启，可以尝试设置延迟为 2000 左右
     */

    private long delay;
    public static final String API = "/set_restart_plugin";
    @Override
    public String getApi(){
        return API;
    }


}
