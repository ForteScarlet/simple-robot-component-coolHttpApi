/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     GetGroupNoticeList.java
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

import com.forte.component.forcoolqhttpapi.beans.result.GroupNoticeList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 获取群公告列表的API
 * 无接口定义
 * 实验性API
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetGroupNoticeList implements Get<GroupNoticeList> {

    /**
     * api请求地址
     */
    public static final String API = "/_get_group_notice";
    private String group_id;


    @Override
    public String getApi() {
        return API;
    }

    @Override
    public Class<GroupNoticeList> getResultType() {
        return GroupNoticeList.class;
    }
}
