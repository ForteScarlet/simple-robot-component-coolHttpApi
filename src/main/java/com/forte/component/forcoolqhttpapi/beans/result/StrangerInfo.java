package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.result.AbstractInfoResult;
import com.forte.qqrobot.beans.messages.types.SexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * 陌生人QQ信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StrangerInfo extends AbstractInfoResult implements com.forte.qqrobot.beans.messages.result.StrangerInfo, Result {
    /*
        响应数据
        字段名	数据类型	说明
        user_id	number (int64)	QQ 号
        nickname	string	昵称
        sex	string	性别，male 或 female 或 unknown
        age	number (int32)	年龄
     */

    private String user_id;
    private String nickname;
    private com.forte.component.forcoolqhttpapi.beans.type.SexType sex;
    private int age;

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
    /** 年龄 */
    @Override
    public Integer getAge(){
        return age;
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
