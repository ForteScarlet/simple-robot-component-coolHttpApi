/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     QQGroupBan.java
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

import com.forte.qqrobot.beans.messages.msgget.GroupBan;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群禁言事件
 * 1.3.5核心暂时还没有此API，等待添加
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.notice, messageType = QQGroupBan.NOTICE_TYPE)
public class QQGroupBan extends BaseMsg implements GroupBan {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	notice	上报类型
        notice_type	string	group_ban	通知类型
        sub_type	string	ban、lift_ban	事件子类型，分别表示禁言、解除禁言
        group_id	number (int64)	-	群号
        operator_id	number (int64)	-	操作者 QQ 号
        user_id	number (int64)	-	被禁言 QQ 号
        duration	number (int64)	-	禁言时长，单位秒
     */

    public static final PostType POST_TYPE = PostType.notice;
    public static final String NOTICE_TYPE = "group_ban";

    private GroupBanType sub_type;
    private String group_id;
    private String operator_id;
    private String user_id;
    private Long duration;

    @Override
    public com.forte.qqrobot.beans.messages.types.GroupBanType getBanType() {
        if (sub_type.equals(GroupBanType.ban)) {
            return com.forte.qqrobot.beans.messages.types.GroupBanType.BAN;
        } else if (sub_type.equals(GroupBanType.lift_ban)) {
            return com.forte.qqrobot.beans.messages.types.GroupBanType.LIFT_BAN;
        } else {
            return null;
        }
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

    @Override
    public Long time() {
        return duration;
    }


    public static enum GroupBanType {
        // ban、lift_ban	事件子类型，分别表示禁言、解除禁言
        ban,
        lift_ban;
    }

}
