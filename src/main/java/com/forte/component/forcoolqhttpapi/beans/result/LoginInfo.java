package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.result.AbstractInfoResult;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class LoginInfo extends AbstractInfoResult implements com.forte.qqrobot.bot.LoginInfo, Result {
    /*
    响应数据
        字段名	数据类型	说明
        user_id	number (int64)	QQ 号
        nickname	string	QQ 昵称
     */

    private String user_id;
    private String nickname;
    private String originalData;
    private Integer level = -1;

    private volatile String headUrl = "";

    /** 昵称 */
    @Override
    public String getName(){
        return nickname;
    };
    /** QQ号 */
    @Override
    public String getQQ(){
        return user_id;
    }

    /**
     * 头像地址
     */
    @Override
    public String getHeadUrl() {
        if("".equals(headUrl)){
            headUrl = user_id == null ? null : "http://q.qlogo.cn/headimg_dl?dst_uin="+ user_id +"&spec=640";
        }
        return headUrl;
    }

    /**
     * 等级
     */
    @Override
    public Integer getLevel() {
        return level;
    }

}



