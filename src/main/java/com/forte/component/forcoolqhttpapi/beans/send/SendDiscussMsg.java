package com.forte.component.forcoolqhttpapi.beans.send;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 发送讨论组消息
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendDiscussMsg implements Send {

    /*
        discuss_id	number	-	讨论组 ID（正常情况下看不到，需要从讨论组消息上报的数据中获得）
        message	message	-	要发送的内容
        auto_escape	boolean	false	消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     */
    private long discuss_id;
    private String message;
    private boolean auto_escape = false;
    private static final String API = "/send_discuss_msg";

    @Override
    public String getApi(){
        return API;
    }



}
