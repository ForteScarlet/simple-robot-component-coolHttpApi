package com.forte.component.forcoolqhttpapi.beans.msg;

import com.forte.qqrobot.beans.messages.msgget.FriendAddRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * 好友添加申请
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.request, messageType = QQFriendAddRequest.REQUEST_TYPE)
public class QQFriendAddRequest extends BaseMsg implements FriendAddRequest {
    /*
    上报数据
    字段名	数据类型	可能的值	说明
    post_type	string	request	上报类型
    request_type	string	friend	请求类型
    user_id	number (int64)	-	发送请求的 QQ 号
    comment	string	-	验证信息（可能包含 CQ 码，特殊字符被转义）
    flag	string	-	请求 flag，在调用处理请求的 API 时需要传入
     */

    public static final PostType POST_TYPE = PostType.request;
    public static final String REQUEST_TYPE = "friend";

    private String user_id;
    private String comment;
    private String flag;


    @Override
    public String getQQ() {
        return user_id;
    }

    @Override
    public String getId() {
        return flag;
    }

    @Override
    public String getMsg() {
        return getComment();
    }

    @Override
    public void setMsg(String msg){
        setComment(msg);
    }
}
