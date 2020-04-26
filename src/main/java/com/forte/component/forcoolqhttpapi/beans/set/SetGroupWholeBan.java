package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 全群禁言
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupWholeBan implements Set {
    /*
            group_id	number	-	群号
            enable	boolean	true	是否禁言
     */
    private String group_id;
    private boolean enable = true;
    public static final String API = "/set_group_whole_ban";

    @Override
    public String getApi() {
        return API;
    }


}
