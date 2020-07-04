/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     Group.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.beans.result.inner;

import com.forte.component.forcoolqhttpapi.beans.result.ResultInner;
import com.forte.qqrobot.beans.messages.result.AbstractResultInner;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群列表中的群信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class Group extends AbstractResultInner implements com.forte.qqrobot.beans.messages.result.inner.Group, ResultInner {
    /*
        字段名	数据类型	说明
        group_id	number (int64)	群号
        group_name	string	群名称
     */

    private String group_id;
    private String group_name;

    /**
     * 群名
     */
    @Override
    public String getName() {
        return group_name;
    }

    /**
     * 群号
     */
    @Override
    public String getCode() {
        return group_id;
    }
}
