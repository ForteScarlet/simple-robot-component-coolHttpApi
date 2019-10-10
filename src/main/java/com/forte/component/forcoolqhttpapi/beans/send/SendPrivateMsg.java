package com.forte.component.forcoolqhttpapi.beans.send;


import lombok.*;

/**
 *
 * 私信消息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendPrivateMsg implements Send {
    /*
    user_id	number	-	对方 QQ 号
    message	message	-	要发送的内容
    auto_escape	boolean	false	消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     */

    private String user_id;
    private String message;
    private boolean auto_escape = false;
    public static final String API = "/send_private_msg";

    public SendPrivateMsg(String user_id, String message){
        this(user_id, message, false);
    }


    @Override
    public String getApi() {
        return API;
    }

//    public long getUser_id() {
////        return user_id;
////    }
////
////    public void setUser_id(String user_id) {
////        this.user_id = user_id;
////    }
////
////    public String getMessage() {
////        return message;
////    }
////
////    public void setMessage(String message) {
////        this.message = message;
////    }
////
////    public boolean isAuto_escape() {
////        return auto_escape;
////    }
////
////    public void setAuto_escape(boolean auto_escape) {
////        this.auto_escape = auto_escape;
////    }
////
////
////    public SendPrivateMsg(String user_id, String message, boolean auto_escape) {
////        this.user_id = user_id;
////        this.message = message;
////        this.auto_escape = auto_escape;
////    }
////
////    public SendPrivateMsg() {
////    }
}
