package com.forte.component.forcoolqhttpapi;

import com.forte.qqrobot.BaseConfiguration;

/**
 * CoolQ HTTP API 对接组件 配置类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpConfiguration extends BaseConfiguration<CoolQHttpConfiguration> {

    /**
     * 端口
     */
    private int port;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRequestPath(){
        return "http://" + getIp() + ":" + port;
    }

}
