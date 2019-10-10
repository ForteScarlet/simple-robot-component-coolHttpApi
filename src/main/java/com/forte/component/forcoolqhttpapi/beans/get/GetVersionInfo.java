package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.VersionInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 *  获取 酷Q 及 HTTP API 插件的版本信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class GetVersionInfo implements Get<VersionInfo> {

    public static final String API = "/get_version_info";
    @Override
    public String getApi(){
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
