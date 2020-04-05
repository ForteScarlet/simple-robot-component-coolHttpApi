package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * 群组踢人
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupKick implements Set {

    /*
        group_id	number	-	群号
        user_id	number	-	要踢的 QQ 号
        reject_add_request	boolean	false	拒绝此人的加群请求
     */
    private String group_id;
    private String user_id;
    private boolean reject_add_request = false;

    public static final String API = "/set_group_kick";

    @Override
    public String getApi(){
        return API;
    }

}
