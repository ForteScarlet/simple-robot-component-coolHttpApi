/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     QQGroupMemberDecrease.java
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

import com.forte.qqrobot.beans.messages.msgget.GroupMemberReduce;
import com.forte.qqrobot.beans.messages.types.ReduceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群成员减少
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.notice, messageType = QQGroupMemberDecrease.NOTICE_TYPE)
public class QQGroupMemberDecrease extends BaseMsg implements GroupMemberReduce {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	notice	上报类型
        notice_type	string	group_decrease	通知类型
        sub_type	string	leave、kick、kick_me	事件子类型，分别表示主动退群、成员被踢、登录号被踢
        group_id	number (int64)	-	群号
        operator_id	number (int64)	-	操作者 QQ 号（如果是主动退群，则和 user_id 相同）
        user_id	number (int64)	-	离开者 QQ 号
     */

    public static final PostType POST_TYPE = PostType.notice;
    public static final String NOTICE_TYPE = "group_decrease";

    private GroupDecreaseType sub_type;
    private String group_id;
    private String operator_id;
    private String user_id;
//    /** 参数中无ID，使用UUID生成一个ID */
//    private String id = UUID.randomUUID().toString();

    @Override
    public ReduceType getType() {
        return sub_type == null ? null : sub_type.type;
    }

    @Override
    public String getGroup() {
        return group_id;
    }

    @Override
    public String getOperatorQQ() {
        return operator_id;
    }

    @Override
    public String getBeOperatedQQ() {
        return user_id;
    }

//    @Override
//    public String getId() {
//        return id;
//    }


    public static enum GroupDecreaseType {
        // leave、kick、kick_me	事件子类型，分别表示主动退群、成员被踢、登录号被踢
        leave(ReduceType.LEAVE),
        kick(ReduceType.KICK_OUT),
        kick_me(ReduceType.KICK_OUT);

        public final ReduceType type;

        GroupDecreaseType(ReduceType t) {
            this.type = t;
        }

    }

}
