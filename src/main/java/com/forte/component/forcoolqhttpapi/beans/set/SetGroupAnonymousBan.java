package com.forte.component.forcoolqhttpapi.beans.set;

import com.forte.component.forcoolqhttpapi.beans.Anonymous;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 群匿名禁言
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupAnonymousBan implements Set {
    /*
            group_id	number	-	群号
            anonymous	object	-	可选，要禁言的匿名用户对象（群消息上报的 anonymous 字段）
            anonymous_flag 或 flag	string	-	可选，要禁言的匿名用户的 flag（需从群消息上报的数据中获得）
            duration	number	30 * 60	禁言时长，单位秒，无法取消匿名用户禁言
     */

    private long group_id;
    private Anonymous anonymous;
    private String anonymous_flag;
    private long duration = 30 * 60;
    private static final String API = "/set_group_anonymous_ban";

    @Override
    public String getApi(){
        return API;
    }

}
