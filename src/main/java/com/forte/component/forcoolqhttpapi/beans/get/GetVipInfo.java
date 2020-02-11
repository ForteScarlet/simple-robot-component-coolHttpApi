package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.QQVipInfo;
import lombok.*;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetVipInfo implements Get<QQVipInfo> {

    /*
    参数
    字段名	数据类型	默认值	说明
    user_id	number	-	要查询的 QQ 号
     */

    private String user_id;
    public static final String API = "/_get_vip_info";

    @Override
    public Class<QQVipInfo> getResultType() {
        return QQVipInfo.class;
    }

    @Override
    public String getApi() {
        return API;
    }
}
