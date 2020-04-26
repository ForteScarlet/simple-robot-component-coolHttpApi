package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 设置群名片（群备注）
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupCard implements Set {
    /*
            group_id	number	-	群号
            user_id	number	-	要设置的 QQ 号
            card	string	空	群名片内容，不填或空字符串表示删除群名片
     */
    private String group_id;
    private String user_id;
    private String card;

    public static final String API = "/set_group_card";

    @Override
    public String getApi() {
        return API;
    }
}
