package com.forte.component.forcoolqhttpapi.beans.send;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 消息撤回
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendDeleteMsg implements Send {
    /*
            message_id	number (int32)	-	消息 ID
     */
    private String message_id;
    public static final String API = "/delete_msg";

    @Override
    public String getApi() {
        return API;
    }

}
