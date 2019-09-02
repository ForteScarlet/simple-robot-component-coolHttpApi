package com.forte.component.forcoolqhttpapi.beans.send;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息撤回
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendDeleteMsg implements Send {
    /*
            message_id	number (int32)	-	消息 ID
     */
    private String message_id;
    private static final String API = "/delete_msg";

    @Override
    public String getApi(){
        return API;
    }

}
