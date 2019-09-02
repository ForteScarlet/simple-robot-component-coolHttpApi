package com.forte.component.forcoolqhttpapi.beans;

import com.forte.qqrobot.beans.messages.FlagAble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 匿名消息的消息封装
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Anonymous implements FlagAble {

    /*
        id	number (int64)	匿名用户 ID
        name	string	匿名用户名称
        flag	string	匿名用户 flag，在调用禁言 API 时需要传入
     */

    private String id;
    private String name;
    private String flag;

}
