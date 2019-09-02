package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 群组踢人
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupKick implements Set {

    /*
        group_id	number	-	群号
        user_id	number	-	要踢的 QQ 号
        reject_add_request	boolean	false	拒绝此人的加群请求
     */
    private long group_id;
    private long user_id;
    private boolean reject_add_request = false;

    private static final String API = "/set_group_kick";

    @Override
    public String getApi(){
        return API;
    }

}
