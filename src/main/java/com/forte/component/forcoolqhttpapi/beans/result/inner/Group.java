package com.forte.component.forcoolqhttpapi.beans.result.inner;

import com.forte.component.forcoolqhttpapi.beans.result.ResultInner;
import com.forte.qqrobot.beans.messages.result.AbstractResultInner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 群列表中的群信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * 群头像地址
     */
    @Override
    public String getHeadUrl() {
        return null;
    }
}
