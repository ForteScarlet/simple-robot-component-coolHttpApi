package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.QQCsrfToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取csrfToken
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
public class GetCsrfToken implements Get<QQCsrfToken> {

    private static final String API = "/get_csrf_token";
    @Override
    public String getApi(){
        return API;
    }


    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<QQCsrfToken> getResultType() {
        return QQCsrfToken.class;
    }
}
