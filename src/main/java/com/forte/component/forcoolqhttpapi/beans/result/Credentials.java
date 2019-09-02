package com.forte.component.forcoolqhttpapi.beans.result;

import lombok.Data;

/**
 * qq相关凭证信息
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
public class Credentials implements Result {
    /*
        响应数据
        字段名	数据类型	说明
        cookies	string	Cookies
        csrf_token	number (int32)	CSRF Token
     */

    private String cookies;
    private String csrf_token;


}
