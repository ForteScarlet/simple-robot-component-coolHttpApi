package com.forte.component.forcoolqhttpapi.beans.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class QQVipInfo implements Result {
    /*
    响应数据
    字段名	数据类型	说明
    user_id	number	QQ 号
    nickname	string	昵称
    level	number	QQ 等级
    level_speed	number	等级加速度
    vip_level	number	会员等级
    vip_growth_speed	number	会员成长速度
    vip_growth_total	string	会员成长总值
     */

    private String user_id;
    private String nickname;
    private Integer level;
    private Integer level_speed;
    // 可能会出现字符串：'普通用户'
    private String vip_level;
    private Integer vip_growth_speed;
    private Integer vip_growth_total;
    private String originalData;

}
