package com.forte.component.forcoolqhttpapi.beans.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * QQ图片
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class QQImage implements Result {
    /*
        响应数据
        字段名	数据类型	说明
        file	string	下载后的图片文件路径，如 C:\Apps\CoolQ\data\image\6B4DE3DFD1BD271E3297859D41C530F5.jpg
     */

    private String file;
    private String originalData;



}
