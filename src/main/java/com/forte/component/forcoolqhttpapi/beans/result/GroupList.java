package com.forte.component.forcoolqhttpapi.beans.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.forte.qqrobot.beans.messages.result.AbstractInfoResultList;
import com.forte.qqrobot.beans.messages.result.inner.Group;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群列表
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class GroupList extends AbstractInfoResultList<Group>
        implements com.forte.qqrobot.beans.messages.result.GroupList,
        ResultList<com.forte.component.forcoolqhttpapi.beans.result.inner.Group> {

    /**
     * list列表
     */
    private com.forte.component.forcoolqhttpapi.beans.result.inner.Group[] list;
    private String originalData;

    /**
     * 获取列表, 极度不建议返回为null
     * non-null
     */
    @Override
    public Group[] getList() {
        return list == null ? new Group[0] : list;
    }

    /**
     * 获取返回列表的值类型
     */
    @Override
    @JSONField(serialize = false)
    public Class<com.forte.component.forcoolqhttpapi.beans.result.inner.Group> getListType() {
        return com.forte.component.forcoolqhttpapi.beans.result.inner.Group.class;
    }

}
