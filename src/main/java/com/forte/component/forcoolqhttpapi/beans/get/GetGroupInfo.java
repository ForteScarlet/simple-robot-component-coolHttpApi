package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.QQGroupInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * 获取群信息
 * 实验性接口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetGroupInfo implements Get<QQGroupInfo> {

    /**
     * 2020/4/12
     * cqhttp 新群信息接口
     */
//    public static final String API = "/_get_group_info";
    public static final String API = "/get_group_info";

    /**
     * 要查询的群号
     */
    private String group_id;

    @Override
    public Class<QQGroupInfo> getResultType() {
        return QQGroupInfo.class;
    }

    @Override
    public String getApi() {
        return API;
    }
}
