/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     SendGroupMsg.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.beans.send;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 群消息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendGroupMsg implements Send {
    /*
        group_id	number	-	对方 QQ 号
        message	message	-	要发送的内容
        auto_escape	boolean	false	消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     */
    private String group_id;
    private String message;
    private boolean auto_escape = false;
    public static final String API = "/send_group_msg";


    public SendGroupMsg(String group_id, String message) {
        this(group_id, message, false);
    }

    @Override
    public String getApi() {
        return API;
    }

}
