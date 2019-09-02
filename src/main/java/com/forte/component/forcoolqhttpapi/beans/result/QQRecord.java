package com.forte.component.forcoolqhttpapi.beans.result;

import lombok.Data;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
public class QQRecord implements Result {
    /*
        响应数据
        字段名	数据类型	说明
        file	string	转换后的语音文件名或路径，如 0B38145AA44505000B38145AA4450500.mp3，如果开启了 full_path，则如 C:\Apps\CoolQ\data\record\0B38145AA44505000B38145AA4450500.mp3
     */

    private String file;


}
