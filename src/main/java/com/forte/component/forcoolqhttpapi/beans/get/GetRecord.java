package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.QQRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 获取语音
 * 其实并不是真的获取语音，而是转换语音到指定的格式，然后返回语音文件名（data\record 目录下）。
 * 注意，要使用此接口，需要安装 酷Q 的 语音组件。
 * <br>
 * 返回值（字符串）：
 * 转换后的语音文件名或路径，如 0B38145AA44505000B38145AA4450500.mp3，
 * 如果开启了 full_path，则如 C:\Apps\CoolQ\data\record\0B38145AA44505000B38145AA4450500.mp3
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRecord implements Get<QQRecord> {
    /*
        参数
        字段名	数据类型	默认值	说明
        file	string	-	收到的语音文件名（CQ 码的 file 参数），如 0B38145AA44505000B38145AA4450500.silk
        out_format	string	-	要转换到的格式，目前支持 mp3、amr、wma、m4a、spx、ogg、wav、flac
        full_path	boolean	false	是否返回文件的绝对路径（Windows 环境下建议使用，Docker 中不建议）
     */

    private String file;
    /**
     * 要转换到的格式，目前支持 mp3、amr、wma、m4a、spx、ogg、wav、flac
     */
    private String out_format;
    private boolean full_path = false;

    public static final String API = "/get_record";

    @Override
    public String getApi() {
        return API;
    }


    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<QQRecord> getResultType() {
        return QQRecord.class;
    }
}
