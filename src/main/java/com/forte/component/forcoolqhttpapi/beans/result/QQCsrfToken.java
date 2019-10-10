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
public class QQCsrfToken implements Result {
    /*
        响应数据
        字段名	数据类型	说明
        token	number (int32)	CSRF Token
     */

    private String token;
    private String originalData;

}
