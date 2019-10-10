package com.forte.component.forcoolqhttpapi.beans.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class QQCookies implements Result {
    /*
        响应数据
        字段名	数据类型	说明
        cookies	string	Cookies
     */

    private String cookies;
    private String originalData;

}
