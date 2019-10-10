package com.forte.component.forcoolqhttpapi.beans.send;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendLike implements Send {
    /*
        user_id	number	-	对方 QQ 号
        times	number	1	赞的次数，每个好友每天最多 10 次
     */
    private String user_id;
    private int times = 1;
    public static final String API = "/send_like";

    @Override
    public String getApi(){
        return API;
    }


}
