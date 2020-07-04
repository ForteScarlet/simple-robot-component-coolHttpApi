/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     QQGroupMemberIncrease.java
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

import com.forte.qqrobot.beans.messages.msgget.GroupMemberIncrease;
import com.forte.qqrobot.beans.messages.types.IncreaseType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群成员增加
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.notice, messageType = QQGroupMemberIncrease.NOTICE_TYPE)
public class QQGroupMemberIncrease extends BaseMsg implements GroupMemberIncrease {
    /*
    字段名	数据类型	可能的值	说明
    post_type	string	notice	上报类型
    notice_type	string	group_increase	通知类型
    sub_type	string	approve、invite	事件子类型，分别表示管理员已同意入群、管理员邀请入群
    group_id	number (int64)	-	群号
    operator_id	number (int64)	-	操作者 QQ 号
    user_id	number (int64)	-	加入者 QQ 号
     */

    public static final PostType POST_TYPE = PostType.notice;
    public static final String NOTICE_TYPE = "group_increase";

    private GroupIncreaseType sub_type;
    private String group_id;
    private String operator_id;
    private String user_id;
//    /** 不存在ID，生成一个UUID */
//    private String id = UUID.randomUUID().toString();

    @Override
    public IncreaseType getType() {
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


    public static enum GroupIncreaseType {
        // approve、invite	事件子类型，分别表示管理员已同意入群、管理员邀请入群
        approve(IncreaseType.AGREE),
        invite(IncreaseType.INVITE);


        public IncreaseType type;

        GroupIncreaseType(IncreaseType t) {
            this.type = t;
        }

    }
}
