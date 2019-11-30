package com.forte.component.forcoolqhttpapi.beans.msg;

import com.forte.qqrobot.beans.messages.msgget.FriendAdd;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 *
 * 好友添加事件
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.notice, messageType = QQFriendAdd.NOTICE_TYPE)
public class QQFriendAdd extends BaseMsg implements FriendAdd {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	notice	上报类型
        notice_type	string	friend_add	通知类型
        user_id	number (int64)	-	新添加好友 QQ 号
     */

    public static final PostType POST_TYPE = PostType.notice;
    public static final String NOTICE_TYPE = "friend_add";

    private String user_id;
//    /** 随机一个ID */
//    private String id = UUID.randomUUID().toString();


    @Override
    public String getQQ() {
        return user_id;
    }

//    @Override
//    public String getId() {
//        return id;
//    }
}
