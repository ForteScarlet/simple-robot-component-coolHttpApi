/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     GetFriendList.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.QQFriendList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 获取好友列表，属于实验性API
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetFriendList implements Get<QQFriendList> {

    public static final GetFriendList INSTANCE = new GetFriendList();

    /**
     * api
     */
    public static final String API = "/_get_friend_list";

    /**
     * 是否获取扁平化的好友数据，即所有好友放在一起、所有分组放在一起，而不是按分组层级
     */
    private Boolean flat = false;

    @Override
    public Class<QQFriendList> getResultType() {
        return QQFriendList.class;
    }

    @Override
    public String getApi() {
        return API;
    }
}
