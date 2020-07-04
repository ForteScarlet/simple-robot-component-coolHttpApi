/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     QQGroupAddRequest.java
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

import com.forte.qqrobot.beans.messages.msgget.GroupAddRequest;
import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 加群申请
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Setter
@Getter
@ToString
@MsgOn(type = PostType.request, messageType = QQGroupAddRequest.REQUEST_TYPE)
public class QQGroupAddRequest extends BaseMsg implements GroupAddRequest {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	request	上报类型
        request_type	string	group	请求类型
        sub_type	string	add、invite	请求子类型，分别表示加群请求、邀请登录号入群
        group_id	number (int64)	-	群号
        user_id	number (int64)	-	发送请求的 QQ 号
        comment	string	-	验证信息（可能包含 CQ 码，特殊字符被转义）
        flag	string	-	请求 flag，在调用处理请求的 API 时需要传入
     */

    public static final PostType POST_TYPE = PostType.request;
    public static final String REQUEST_TYPE = "group";

    private GroupAddReqType sub_type;
    private String group_id;
    private String user_id;
    private String comment;
    private String flag;

    @Override
    public String getGroup() {
        return group_id;
    }

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
    public void setMsg(String msg) {
        setComment(msg);
    }

    @Override
    public GroupAddRequestType getRequestType() {
        return sub_type == null ? null : sub_type.type;
    }


    public static enum GroupAddReqType {
        // add、invite
        add(GroupAddRequestType.ADD),
        invite(GroupAddRequestType.INVITE);

        public final GroupAddRequestType type;

        GroupAddReqType(GroupAddRequestType t) {
            this.type = t;
        }
    }

}
