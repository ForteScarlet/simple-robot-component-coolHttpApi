package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.result.AbstractInfoResultList;
import com.forte.qqrobot.beans.messages.result.inner.GroupMember;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * 群成员列表
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class GroupMemberList
        extends AbstractInfoResultList<GroupMember>
        implements com.forte.qqrobot.beans.messages.result.GroupMemberList,
        ResultList<MemberInfo> {

    private MemberInfo[] list;
    private String originalData;

    @Override
    public GroupMember[] getList(){
        return list;
    }

    /**
     * 获取数组类型
     */
    @Override
    public Class<MemberInfo> getListType() {
        return MemberInfo.class;
    }
}
