package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.types.SexType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * 陌生人QQ信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@ToString
public class StrangerInfo implements com.forte.qqrobot.beans.messages.result.StrangerInfo, Result {
    /*
        响应数据
        字段名	数据类型	说明
        user_id	number (int64)	QQ 号
        nickname	string	昵称
        sex	string	性别，male 或 female 或 unknown
        age	number (int32)	年龄
     */

    @Getter@Setter private String user_id;
    @Getter@Setter private String nickname;
    @Setter        private com.forte.component.forcoolqhttpapi.beans.type.SexType sex;
    @Getter@Setter private Integer age;
    @Getter@Setter private String originalData;

    /** QQ号 */
    @Override
    public String getQQ(){
        return user_id;
    }
    /** 性别 */
    @Override
    public SexType getSex(){
        if(sex.equals(com.forte.component.forcoolqhttpapi.beans.type.SexType.male)){
            return SexType.MALE;
        }else if(sex.equals(com.forte.component.forcoolqhttpapi.beans.type.SexType.female)){
            return SexType.FEMALE;
        }else{
            return SexType.UNKNOWN;
        }
    }

    /**
     * 头像地址
     */
    @Override
    public String headUrl() {
        return null;
    }

    /**
     * 等级
     */
    @Override
    public Integer getLevel() {
        return -1;
    }

    /** 获取名称（昵称） */
    @Override
    public String getName(){
        return nickname;
    }
}
