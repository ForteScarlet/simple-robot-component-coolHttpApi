package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.component.forcoolqhttpapi.beans.type.GroupMemberRoleType;
import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.forte.qqrobot.beans.messages.result.inner.GroupMember;
import com.forte.qqrobot.beans.messages.types.PowerType;
import com.forte.qqrobot.beans.messages.types.SexType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群成员详细信息
 * 也作为群成员列表的数据
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@ToString
public class MemberInfo
        implements GroupMemberInfo,
        ResultInner,
        GroupMember {
    /*
        group_id	number (int64)	群号
        user_id	number (int64)	QQ 号
        nickname	string	昵称
        card	string	群名片／备注
        sex	string	性别，male 或 female 或 unknown
        age	number (int32)	年龄
        area	string	地区
        join_time	number (int32)	加群时间戳
        last_sent_time	number (int32)	最后发言时间戳
        level	string	成员等级
        role	string	角色，owner 或 admin 或 member
        unfriendly	boolean	是否不良记录成员
        title	string	专属头衔
        title_expire_time	number (int32)	专属头衔过期时间戳
        card_changeable	boolean	是否允许修改群名片
     */

    @Getter
    @Setter
    private String group_id;
    @Getter
    @Setter
    private String user_id;
    @Setter
    private String nickname;
    @Getter
    @Setter
    private String card;
    @Getter
    @Setter
    private String originalData;


    @Setter
    private com.forte.component.forcoolqhttpapi.beans.type.SexType sex;
    @Getter
    @Setter
    private int age;
    @Getter
    @Setter
    private String area;
    @Getter
    @Setter
    private long join_time;
    @Getter
    @Setter
    private long last_sent_time;
    @Getter
    @Setter
    private String level;
    @Getter
    @Setter
    private GroupMemberRoleType role;
    @Getter
    @Setter
    private boolean unfriendly;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private long title_expire_time;
    @Getter
    @Setter
    private boolean card_changeable;


    /**
     * 获取群号
     */
    @Override
    public String getCode() {
        return group_id;
    }

    /**
     * 群号
     */
    @Override
    public String getGroup() {
        return group_id;
    }

    @Override
    public SexType getSex() {
        if (sex.equals(com.forte.component.forcoolqhttpapi.beans.type.SexType.male)) {
            return SexType.MALE;
        } else if (sex.equals(com.forte.component.forcoolqhttpapi.beans.type.SexType.female)) {
            return SexType.FEMALE;
        } else {
            return SexType.UNKNOWN;
        }

    }

    /**
     * 成员QQ号
     */
    @Override
    public String getQQ() {
        return user_id;
    }



    /**
     * 所在城市
     */
    @Override
    public String getCity() {
        return area;
    }

    /**
     * 加群时间
     */
    @Override
    public Long getJoinTime() {
        return join_time;
    }

    /**
     * 最后一次发言时间
     */
    @Override
    public Long getLastTime() {
        return last_sent_time;
    }

    /**
     * 权限类型
     */
    @Override
    public PowerType getPower() {
        return getPowerType();
    }

    /**
     * 权限类型
     */
    @Override
    public PowerType getPowerType() {
        if (GroupMemberRoleType.owner.equals(role)) {
            return PowerType.OWNER;
        } else if (GroupMemberRoleType.admin.equals(role)) {
            return PowerType.ADMIN;
        } else {
            return PowerType.MEMBER;
        }
    }

    /**
     * 获取专属头衔
     */
    @Override
    public String getExTitle() {
        return title;
    }

    /**
     * 群成员等级名称
     */
    @Override
    public String getLevelName() {
        return level;
    }

    /**
     * 是否为不良用户
     */
    @Override
    public Boolean isBlack() {
        return unfriendly;
    }

    /**
     * 是否允许修改群昵称
     */
    @Override
    public Boolean isAllowChangeNick() {
        return card_changeable;
    }

    /**
     * 头衔的有效期
     */
    @Override
    public Long getExTitleTime() {
        return title_expire_time;
    }

    /**
     * 头像
     */
    @Override
    public String getHeadUrl() {
        return getQQHeadUrl();
    }

    /**
     * 头像地址
     */
    @Override
    public String getHeadImgUrl() {
        return getQQHeadUrl();
    }

    /**
     * 禁言剩余时间
     * 无此字段
     */
    @Override
    public Long getBanTime() {
        return -1L;
    }

    @Override
    public String getGroupCode() {
        return getGroup();
    }

    @Override
    public String getQQCode() {
        return getQQ();
    }

    /**
     * 可以获取昵称
     *
     * @return nickname
     */
    @Override
    public String getNickname() {
        return nickname;
    }

    /**
     * 获取备注信息，例如群备注，或者好友备注。
     *
     * @return 备注信息
     */
    @Override
    public String getRemark() {
        return card;
    }
}
