/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     SetGroupAnonymousBan.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.beans.set;

import com.forte.component.forcoolqhttpapi.beans.msg.Anonymous;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 群匿名禁言
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class SetGroupAnonymousBan implements Set {
    /*
            group_id	number	-	群号
            anonymous	object	-	可选，要禁言的匿名用户对象（群消息上报的 anonymous 字段）
            anonymous_flag 或 flag	string	-	可选，要禁言的匿名用户的 flag（需从群消息上报的数据中获得）
            duration	number	30 * 60	禁言时长，单位秒，无法取消匿名用户禁言
     */

    private String group_id;
    private Anonymous anonymous;
    private String anonymous_flag;
    private long duration = 30 * 60;
    public static final String API = "/set_group_anonymous_ban";

    public SetGroupAnonymousBan(String group_id, String flag, long time) {
        this.group_id = group_id;
        this.anonymous_flag = flag;
        this.duration = time;
    }

    public SetGroupAnonymousBan(String group_id, Anonymous flag, long time) {
        this.group_id = group_id;
        this.anonymous = flag;
        this.duration = time;
    }


    @Override
    public String getApi() {
        return API;
    }

}
