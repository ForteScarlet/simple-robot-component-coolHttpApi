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
