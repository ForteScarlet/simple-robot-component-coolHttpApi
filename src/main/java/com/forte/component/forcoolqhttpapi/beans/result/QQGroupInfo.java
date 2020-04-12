package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.result.GroupInfo;
import com.forte.qqrobot.beans.messages.types.PowerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 *
 * 群详细信息
 * 实验性接口
 *
 * 原文档说明文字：
 *
 * 注意，和其它接口有所不同，这里的所有字段都有可能在返回数据中不存在，例如可能缺少 max_member_count 等，在使用时请注意异常处理。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class QQGroupInfo implements Result, GroupInfo {
    /*
        响应数据
        字段名	数据类型	说明
        group_id	number	群号
        group_name	string	群名称
        create_time	number	创建时间
        category	number	群分类，具体这个 ID 对应的名称暂时没有
        member_count	number	成员数
        max_member_count	number	最大成员数（群容量）
        introduction	string	群介绍
        admins	array	群主和管理员列表
        admin_count	number	群主和管理员数
        max_admin_count	number	最大群主和管理员数
        owner_id	number	群主 QQ 号

        其中，admins 中每一项是一个 JSON 对象，如下：

        字段名	数据类型	说明
        user_id	number	QQ 号
        nickname	string	昵称
        role	string	角色，owner 表示群主、admin 表示管理员
     */

    /*
        2020/4/12 新get_group_info内容
        /get_group_info 获取群信息

        响应数据
        字段名	数据类型	说明
        group_id	number (int64)	群号
        group_name	string	群名称
        member_count	number (int32)	成员数
        max_member_count	number (int32)	最大成员数（群容量）
     */

    private String group_id;
    private String group_name;
    private Long create_time;
    private Integer category;
    private Integer member_count;
    private Integer max_member_count;
    private String introduction;
    private QQGroupAdmin[] admins;
    private Integer admin_count;
    private Integer max_admin_count;
    private String owner_id;
    /** 原始数据 */
    private String originalData;

    /**
     * 不支持的数据
     */
    @Override
    @Deprecated
    public Integer getLevel() {
        return -1;
    }

    /**
     * 不支持的数据
     */
    @Override
    @Deprecated
    public Integer getOpenType() {
        return -1;
    }

    /**
     * 群类型
     */
    @Override
    public String getType() {
        return "" + category;
    }

    /**
     * 群类型ID
     */
    @Override
    public Integer getTypeId() {
        return category;
    }

    /**
     * 群管理员列表
     */
    @Override
    public String[] getAdminList() {
        return Arrays.stream(admins)
                .filter(a -> a.getPowerType().equals(PowerType.ADMIN))
                .map(QQGroupAdmin::getUser_id)
                .toArray(String[]::new);
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public String getBoard() {
        return null;
    }


    /**
     * 创建时间
     */
    @Override
    public Long getCreateTime() {
        return create_time;
    }

    /**
     * 不存在简略群简介
     * @deprecated
     * @see #getCompleteIntro()
     */
    @Override
    @Deprecated
    public String getSimpleIntro() {
        return introduction;
    }

    /**
     * 完整群简介
     */
    @Override
    public String getCompleteIntro() {
        return introduction;
    }

    /**
     * 获取最大群人数
     * @return 最大群人数
     */
    @Override
    public Integer getMaxMember() {
        return max_member_count;
    }


    /**
     * 群人数
     */
    @Override
    public Integer getMemberNum() {
        return member_count;
    }

    /**
     * 群名称
     */
    @Override
    public String getName() {
        return group_name;
    }

    /**
     * 获取群主QQ
     * @return 群主QQ
     */
    @Override
    public String getOwnerQQ() {
        if(owner_id != null){
            return owner_id;
        }else{
            // 如果是null，尝试从admin中获取并重新为owner_id赋值
            if(admins != null){
                for (QQGroupAdmin admin : admins) {
                    // 是群主
                    if(admin.getPowerType().equals(PowerType.OWNER)){
                        owner_id = admin.getUser_id();
                        return owner_id;
                    }
                }
                return null;
            }else{
                return null;
            }
        }
    }

    /**
     * 获取群号
     * @return 群号
     */
    @Override
    public String getCode() {
        return group_id;
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public Map<String, String> getLevelNames() {
        return Collections.emptyMap();
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public Map<String, String> getAdminNickList() {
        return Collections.emptyMap();
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public String getPos() {
        return null;
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public Integer getSearchType() {
        return -1;
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public String[] getTags() {
        return new String[0];
    }


    /**
     * 对应 admins数组的对象，代表管理员或者群主
     */
    @Getter
    @Setter
    @ToString
    public static class QQGroupAdmin implements Comparable<QQGroupAdmin> {

        /*
             字段名	数据类型	说明
            user_id	number	QQ 号
            nickname	string	昵称
            role	string	角色，owner 表示群主、admin 表示管理员
         */

        private String user_id;
        private String nickname;
        private String role;
        private static final String OWNER_ROLE = "owner";
        private static final String ADMIN_ROLE = "admin";


        /**
         * 获取管理者权限类型
         * @return 将role作为{@link PowerType} 类型返回
         */
        public PowerType getPowerType(){
            if(role == null){
                // 如果无法判别，则判定为群员
                return PowerType.MEMBER;
            }else{
                switch (role) {
                    case OWNER_ROLE : return PowerType.OWNER;
                    case ADMIN_ROLE : return PowerType.ADMIN;
                    default: return PowerType.MEMBER;
                }
            }
        }

        /**
         * 可排序的, 排序值: 群主: 1, 管理员: 0
         *  如果存在群员类型，则群员为-1
         * @param o
         * @return
         */
        @Override
        public int compareTo(QQGroupAdmin o) {
            return Integer.compare(getPowerType().TYPE, o.getPowerType().TYPE);
        }
    }
}
