/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     QQPrivateMsg.java
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
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.PrivateMsgType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 私信消息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
@MsgOn(type = PostType.message, messageType = QQPrivateMsg.MESSAGE_TYPE)
public class QQPrivateMsg extends BaseMsg implements PrivateMsg {
    /*
        post_type	string	message	上报类型
        message_type	string	private	消息类型
        sub_type	string	friend、group、discuss、other
            消息子类型，如果是好友则是 friend，如果从群或讨论组来的临时会话则分别是 group、discuss
        message_id	number (int32)	-	消息 ID
        user_id	number (int64)	-	发送者 QQ 号
        message	message	-	消息内容
        raw_message	string	-	原始消息内容
        font	number (int32)	-	字体
        sender	object	-	发送人信息
     */
    /**
     * 上报类型必定为message
     */
    public static final PostType POST_TYPE = PostType.message;
    /**
     * 消息类型 - 私信
     */
    public static final String MESSAGE_TYPE = "private";

    private String post_type;
    /**
     * 子类型
     */
    private PriType sub_type;

    private String message_id;

    /**
     * qq号
     */
    private String user_id;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 原始消息内容
     */
    private String raw_message;
    /**
     * 字体类型
     */
    private String font;

    /**
     * 送信者信息封装
     */
    private Sender sender;

    @Override
    public PrivateMsgType getType() {
        // 消息子类型，如果是好友则是 friend，如果从群或讨论组来的临时会话则分别是 group、discuss
        return sub_type == null ? null : sub_type.type;
    }

    /**
     * 送信人QQ
     */
    @Override
    public String getQQ() {
        return sender == null ? null : sender.getUser_id();
    }

    @Override
    public String getFlag() {
        return getId();
    }

    @Override
    public String getId() {
        return message_id;
    }

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public void setMsg(String msg) {
        this.message = msg;
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

    /**
     * 私信的消息内容封装类
     */
    @Getter
    @Setter
    @ToString
    public static class Sender implements QQCodeAble {
        /*
        user_id	number (int64)	发送者 QQ 号
        nickname	string	昵称
        sex	string	性别，male 或 female 或 unknown
        age	number (int32)	年龄
         */

        private String user_id;
        private String nickname;
        private String sex;
        private int age;

        @Override
        public String getQQCode() {
            return user_id;
        }
    }

    public static enum PriType {
        //消息子类型，如果是好友则是 friend，如果从群或讨论组来的临时会话则分别是 group、discuss
        friend(PrivateMsgType.FROM_FRIEND),
        group(PrivateMsgType.FROM_GROUP),
        discuss(PrivateMsgType.FROM_DISCUSS);
        public final PrivateMsgType type;

        /**
         * 构建对应的PrivateMsgType类型
         */
        PriType(PrivateMsgType type) {
            this.type = type;
        }
    }

}
