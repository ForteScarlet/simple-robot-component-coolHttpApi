/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     QQDiscussMsg.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.beans.msg;

import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.msgget.DiscussMsg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 讨论组消息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
@MsgOn(type = PostType.message, messageType = QQDiscussMsg.MESSAGE_TYPE)
public class QQDiscussMsg extends BaseMsg implements DiscussMsg {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	message	上报类型
        message_type	string	discuss	消息类型
        message_id	number (int32)	-	消息 ID
        discuss_id	number (int64)	-	讨论组 ID
        user_id	number (int64)	-	发送者 QQ 号
        message	message	-	消息内容
        raw_message	string	-	原始消息内容
        font	number (int32)	-	字体
        sender	object
     */

    public static final PostType POST_TYPE = PostType.message;
    public static final String MESSAGE_TYPE = "discuss";

    private String message_id;
    private String discuss_id;
    private String user_id;
    private String message;
    private String raw_message;
    private String font;
    private Sender sender;

    @Override
    public String getGroup() {
        return discuss_id;
    }

    @Override
    public String getQQ() {
        return user_id;
    }

    @Override
    public String getId() {
        return message_id;
    }

    @Override
    public String getMsg() {
        return message;
    }

    /**
     * 重新设置消息
     *
     * @param newMsg msg
     * @since 1.7.x
     */
    @Override
    public void setMsg(String newMsg) {
        message = newMsg;
    }

    /**
     * 可以获取昵称
     *
     * @return nickname
     */
    @Override
    public String getNickname() {
        return sender == null ? null : sender.nickname;
    }


    /**
     * @see #getNickname()
     * @return 备注信息
     */
    @Override
    public String getRemark() {
        return getNickname();
    }

    /**
     * @see #getNickname()
     */
    @Override
    public String getRemarkOrNickname() {
        return getNickname();
    }


    @Getter
    @Setter
    @ToString
    public static class Sender implements QQCodeAble {
        /*
            其中 sender 字段的内容如下：
            字段名	数据类型	说明
            user_id	number (int64)	发送者 QQ 号
            nickname	string	昵称
            sex	string	性别，male 或 female 或 unknown
            age	number (int32)	年龄
         */

        private String user_id;
        private String nickname;
        private String sex;
        private Integer age;

        @Override
        public String getQQCode() {
            return user_id;
        }
    }

}
