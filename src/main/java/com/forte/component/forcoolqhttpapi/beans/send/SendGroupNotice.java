package com.forte.component.forcoolqhttpapi.beans.send;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 发布群公告
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendGroupNotice implements Send {
    /*
        字段名	数据类型	默认值	说明
        group_id	number	-	群号
        title	string	-	标题
        content	string	-	内容
     */
    private String group_id;
    private String title;
    private String content;

    private static final String API = "/_send_group_notice";

    @Override
    public String getApi() {
        return API;
    }
}
