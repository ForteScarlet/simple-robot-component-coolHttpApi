package com.forte.component.forcoolqhttpapi.beans.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 是否可以发图片
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class CanSendImage implements Result {
    /*
    响应数据
    字段名	数据类型	说明
    yes	boolean	是或否
     */

    private boolean yes;
    private String originalData;


}
