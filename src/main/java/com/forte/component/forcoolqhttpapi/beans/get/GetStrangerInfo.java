package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.StrangerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStrangerInfo implements Get<StrangerInfo> {

    /*
    参数
    字段名	数据类型	默认值	说明
    user_id	number	-	QQ 号
    no_cache	boolean	false	是否不使用缓存（使用缓存可能更新不及时，但响应更快
     */
    private long user_id;
    private boolean no_cache = false;

    private static final String API = "/get_stranger_info";
    @Override
    public String getApi(){
        return API;
    }


    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<StrangerInfo> getResultType() {
        return StrangerInfo.class;
    }
}
