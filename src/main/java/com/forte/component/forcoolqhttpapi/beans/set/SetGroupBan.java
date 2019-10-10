package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 群单人禁言
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupBan implements Set {
    /*
        group_id	number	-	群号
        user_id	number	-	要禁言的 QQ 号
        duration	number	30 * 60	禁言时长，单位秒，0 表示取消禁言
     */

    private String group_id;
    private String user_id;
    /** 单位秒，0为取消禁言 默认值为30 * 60 */
    private long duration = 30 * 60;

    public static final String API = "/set_group_ban";

    @Override
    public String getApi(){
        return API;
    }

    /**
     * 如果小于0，设置为0
     */
    public void setDuration(long time){
        if(time <= 0){
            this.duration = 0L;
        }else{
            this.duration = time;
        }
    }

}
