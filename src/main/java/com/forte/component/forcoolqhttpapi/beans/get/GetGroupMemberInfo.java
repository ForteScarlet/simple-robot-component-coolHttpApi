package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.MemberInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 获取群成员信息
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetGroupMemberInfo implements Get<MemberInfo> {
    /*
        group_id	number	-	群号
        user_id	number	-	QQ 号
        no_cache	boolean	false	是否不使用缓存（使用缓存可能更新不及时，但响应更快）
     */

    private String group_id;
    private String user_id;
    private boolean no_cache = false;

    public static final String API = "/get_group_member_info";
    @Override
    public String getApi(){
        return API;
    }


    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<MemberInfo> getResultType() {
        return MemberInfo.class;
    }
}
