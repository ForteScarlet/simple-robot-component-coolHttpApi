package com.forte.component.forcoolqhttpapi.beans.msg;

import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.GroupMsgType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * 群消息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
@MsgOn(type = PostType.message, messageType = QQGroupMsg.MESSAGE_TYPE)
public class QQGroupMsg extends BaseMsg implements GroupMsg {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	message	上报类型
        message_type	string	group	消息类型
        sub_type	string	normal、anonymous、notice	消息子类型，正常消息是 normal，匿名消息是 anonymous，系统提示（如「管理员已禁止群内匿名聊天」）是 notice
        message_id	number (int32)	-	消息 ID
        group_id	number (int64)	-	群号
        user_id	number (int64)	-	发送者 QQ 号
        anonymous	object	-	匿名信息，如果不是匿名消息则为 null
        message	message	-	消息内容
        raw_message	string	-	原始消息内容
        font	number (int32)	-	字体
        sender	object	-	发送人信息
     */
    /** 上报类型必定为message */
    public static final PostType POST_TYPE = PostType.message;
    /** 消息类型 - 群消息 */
    public static final String MESSAGE_TYPE = "group";

    private GroupType sub_type;
    private String message_id;
    private String group_id;
    private String user_id;
    private Anonymous anonymous;
    private String message;
    private String raw_message;
    private String font;
    private Sender sender;

    @Override
    public String getQQ() {
        return user_id;
    }

    @Override
    public String getGroup() {
        return group_id;
    }

    @Override
    public GroupMsgType getType() {
        // sub_type	string
        // normal、anonymous、notice
        // 消息子类型，正常消息是 normal，
        // 匿名消息是 anonymous，
        // 系统提示（如「管理员已禁止群内匿名聊天」）是 notice
        return sub_type == null ? null : sub_type.groupMsgType;
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
     * 群消息中的sender字段
     */
    @Getter
    @Setter
    @ToString
    public static class Sender implements QQCodeAble {
        /*
        sender 字段的内容如下：

        字段名	数据类型	说明
        user_id	number (int64)	发送者 QQ 号
        nickname	string	昵称
        card	string	群名片／备注
        sex	string	性别，male 或 female 或 unknown
        age	number (int32)	年龄
        area	string	地区
        level	string	成员等级
        role	string	角色，owner 或 admin 或 member
        title	string	专属头衔
         */

        private String user_id;
        private String nickname;
        private String card;
        private String sex;
        private Integer age;
        private String area;
        private String level;
        private String role;
        private String title;

        @Override
        public String getQQCode() {
            return user_id;
        }
    }

    /**
     * 群消息中的sub_type字段的枚举类型
     */
    public static enum GroupType {
        //消息子类型，
        // 正常消息是 normal，
        // 匿名消息是 anonymous，
        // 系统提示（如「管理员已禁止群内匿名聊天」）是 notice
        normal(GroupMsgType.NORMAL_MSG),
        anonymous(GroupMsgType.ANON_MSG),
        notice(GroupMsgType.NORMAL_MSG)
        ;

        public final GroupMsgType groupMsgType;

        GroupType(GroupMsgType t){
            this.groupMsgType = t;
        }

    }

}
