package com.forte.component.forcoolqhttpapi.beans.result;

import lombok.Data;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
public class CanSendRecord implements Result {
    /*
        响应数据
        字段名	数据类型	说明
        yes	boolean	是或否
     */

    private boolean yes;

}
