package com.forte.component.forcoolqhttpapi.beans.set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 退出讨论组
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetDiscussLeave implements Set {
    /*
        discuss_id	number	-	讨论组 ID（正常情况下看不到，需要从讨论组消息上报的数据中获得）
     */
    private String discuss_id;

    public static final String API = "/set_discuss_leave";

    @Override
    public String getApi() {
        return API;
    }
}
