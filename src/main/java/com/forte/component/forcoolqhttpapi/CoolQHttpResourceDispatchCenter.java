package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.component.forcoolqhttpapi.server.SendJsonCreator;
import com.forte.qqrobot.ResourceDispatchCenter;

/**
 * HTTP API使用的资源调度器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/15 17:15
 * @since JDK1.8
 **/
public class CoolQHttpResourceDispatchCenter extends ResourceDispatchCenter {
    /**
     * 保存一个Http配置类
     *
     * @param httpConfiguration Http配置类
     */
    static void saveCoolQHttpConfiguration(CoolQHttpConfiguration httpConfiguration) {
        saveConfiguration(httpConfiguration);
    }
    //**************** get ****************//
    /**
     * 获取一个HttpConfiguration单例对象
     *
     * @return HttpConfiguration单例对象
     */
    public static CoolQHttpConfiguration getCoolQHttpConfiguration() {
        return get(CoolQHttpConfiguration.class);
    }



}
