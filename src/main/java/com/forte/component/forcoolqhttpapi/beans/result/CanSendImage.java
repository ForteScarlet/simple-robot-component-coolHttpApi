package com.forte.component.forcoolqhttpapi.beans.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 是否可以发图片
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
public class CanSendImage implements Result {
    /*
    响应数据
    字段名	数据类型	说明
    yes	boolean	是或否
     */

    private boolean yes;



}
