package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 设置群管理员
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupAdmin implements Set {
    /*
        group_id	number	-	群号
        user_id	number	-	要设置管理员的 QQ 号
        enable	boolean	true	true 为设置，false 为取消
     */
    private String group_id;
    private String user_id;
    private boolean enable = true;
    public static final String API = "/set_group_admin";
    @Override
    public String getApi(){
        return API;
    }

}
