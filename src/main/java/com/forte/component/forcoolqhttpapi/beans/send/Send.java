package com.forte.component.forcoolqhttpapi.beans.send;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 定义所有送信的消息类型
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface Send {

    /**
     * coolq http api中可以通过附加后缀 _async 来实现异步
     */
    @JSONField(serialize = false)
    String ASYNC_API = "_async";

    /**
     * coolq http api中可以通过附加后缀 _rate_limited 来实现限速
     */
    @JSONField(serialize = false)
    String LIMIT_API = "_rate_limited";

    /**
     * 获取此接口对应的请求API地址
     */
    @JSONField(serialize = false)
    String getApi();

    /**
     * 获取异步API地址
     */
    @JSONField(serialize = false)
    default String getAsyncApi() {
        return getApi() + ASYNC_API;
    }

    /**
     * 获取限速API地址
     */
    @JSONField(serialize = false)
    default String getLimitApi() {
        return getApi() + LIMIT_API;
    }


}
