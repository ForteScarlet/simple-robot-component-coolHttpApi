/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     Credentials.java
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

import com.forte.qqrobot.beans.messages.result.AuthInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * qq相关凭证信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class Credentials implements Result, AuthInfo {
    /*
        响应数据
        字段名	数据类型	说明
        cookies	string	Cookies
        csrf_token	number (int32)	CSRF Token
     */

    private String cookies;
    private String csrf_token;
    private String originalData;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getCsrfToken() {
        return csrf_token;
    }
}
