package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.QQCookies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 获取Cookie信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
public class GetCookies implements Get<QQCookies> {

    private static final String API = "/get_cookies";
    @Override
    public String getApi(){
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
