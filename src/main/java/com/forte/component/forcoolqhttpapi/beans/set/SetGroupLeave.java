package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退出群
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupLeave implements Set {

    /*
            group_id	number	-	群号
            is_dismiss	boolean	false	是否解散，如果登录号是群主，则仅在此项为 true 时能够解散
     */

    private long group_id;
    private boolean is_dismiss = false;


    private static final String API = "/set_group_leave";
    @Override
    public String getApi(){
        return API;
    }

}
