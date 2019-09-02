package com.forte.component.forcoolqhttpapi.beans.type;

/**
 *
 * 群成员的权限级别类型
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public enum GroupMemberRoleType {
    /*
        string	角色，owner 或 admin 或 member
     */
    /** 群主 */
    owner,
    /** 管理员 */
    admin,
    /** 群员 */
    member
    ;

}
