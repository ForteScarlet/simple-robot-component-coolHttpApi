package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetFriendAddRequest implements Set {
    /*
            flag	string	-	加好友请求的 flag（需从上报的数据中获得）
            approve	boolean	true	是否同意请求
            remark	string	空	添加后的好友备注（仅在同意时有效）
     */

    private String flag;
    private boolean approve = true;
    private String remark;


    private static final String API = "/set_friend_add_request";
    @Override
    public String getApi(){
        return API;
    }

}
