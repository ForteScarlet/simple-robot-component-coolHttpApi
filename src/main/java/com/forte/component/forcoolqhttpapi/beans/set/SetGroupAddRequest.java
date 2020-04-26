package com.forte.component.forcoolqhttpapi.beans.set;

import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 处理加群请求／邀请
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetGroupAddRequest implements Set {

    /*
        flag	string	-	加群请求的 flag（需从上报的数据中获得）
        sub_type 或 type	string	-	add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
        approve	boolean	true	是否同意请求／邀请
        reason	string	空	拒绝理由（仅在拒绝时有效）
     */

    private String flag;
    /**
     * 或者type add or invite TODO 替换为枚举
     */
    private String type;
    private boolean approve = true;
    private String reason;

    public SetGroupAddRequest(String flag, GroupAddRequestType requestType, boolean agree, String why) {
        this.flag = flag;
        // 邀请 或 添加
        this.type = requestType.equals(GroupAddRequestType.INVITE) ? "invite" : "add";
        this.approve = agree;
        this.reason = why;
    }

    public static final String API = "/set_group_add_request";

    @Override
    public String getApi() {
        return API;
    }
}
