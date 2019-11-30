package com.forte.component.forcoolqhttpapi.beans.msg;

import com.forte.qqrobot.beans.messages.msgget.GroupAdminChange;
import com.forte.qqrobot.beans.messages.types.GroupAdminChangeType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 *
 * 群管理变动事件
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.notice, messageType = QQGroupAdminChange.NOTICE_TYPE)
public class QQGroupAdminChange extends BaseMsg implements GroupAdminChange {
    /*
    字段名	数据类型	可能的值	说明
    post_type	string	notice	上报类型
    notice_type	string	group_admin	通知类型
    sub_type	string	set、unset	事件子类型，分别表示设置和取消管理员
    group_id	number (int64)	-	群号
    user_id	number (int64)	-	管理员 QQ 号
     */

    public static final PostType POST_TYPE = PostType.notice;
    public static final String NOTICE_TYPE = "group_admin";

    private AdminChangeType sub_type;
    private String group_id;
    private String user_id;
//    /** 因为参数中不存在id一类的东西，获取一个UUID暂时替代 */
//    private String id = UUID.randomUUID().toString();

    @Override
    public String getGroup() {
        return group_id;
    }

    /**
     * 似乎无操作者信息
     */
    @Override
    @Deprecated
    public String getOperatorQQ() {
        return null;
    }

    @Override
    public String getBeOperatedQQ() {
        return user_id;
    }

    @Override
    public GroupAdminChangeType getType() {
        return sub_type == null ? null : sub_type.type;
    }

    /**
     * 消息ID，参数中不存在，返回一个UUD
     */
//    @Override
//    public String getId() {
//        return id;
//    }


    /**
     * 子类型枚举
     */
    public static enum AdminChangeType {
        // set、unset	事件子类型，分别表示设置和取消管理员
        set(GroupAdminChangeType.BECOME_ADMIN) ,
        unset(GroupAdminChangeType.CANCEL_ADMIN)
        ;


        public final GroupAdminChangeType type;

        AdminChangeType(GroupAdminChangeType t){
            this.type = t;
        }

    }
}
