package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 设置群专属头衔
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupSpecialTitle implements Set {

    /*
            group_id	number	-	群号
            user_id	number	-	要设置的 QQ 号
            special_title	string	空	专属头衔，不填或空字符串表示删除专属头衔
            duration	number	-1	专属头衔有效期，单位秒，-1 表示永久，不过此项似乎没有效果，可能是只有某些特殊的时间长度有效，有待测试
     */
    private long group_id;
    private long user_id;
    private String special_title;
    private long duration = -1;

    private static final String API = "/set_group_special_title";
    @Override
    public String getApi(){
        return API;
    }

}
