package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.Credentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * 获取 QQ 相关接口凭证
 * 获取cookie和csrfToken
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class GetCredentials implements Get<Credentials>  {

    public static final String API = "/get_credentials";
    @Override
    public String getApi(){
        return API;
    }


    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<Credentials> getResultType() {
        return Credentials.class;
    }
}
