package com.forte.component.forcoolqhttpapi;

import com.forte.qqrobot.BaseConfiguration;

/**
 * CoolQ HTTP API 对接组件 配置类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpConfiguration extends BaseConfiguration<CoolQHttpConfiguration> {
    /*
        目前没有进行多账号登录的打算，于是干脆全部使用静态字段
     */


    /**
     * 端口
     */
    private static int javaPort;

    /**
     * 酷Q端插件的端口
     */
    private static int serverPort;

    /**
     * TCP连接最大并发数, 传 0 或负数表示使用默认值
     */
    private static int backLog = 0;


    public static int getBackLog() {
        return backLog;
    }

    public void setBackLog(int backLog) {
        CoolQHttpConfiguration.backLog = backLog;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        CoolQHttpConfiguration.serverPort = serverPort;
    }

    public static int getJavaPort() {
        return javaPort;
    }

    public void setJavaPort(int javaPort) {
        CoolQHttpConfiguration.javaPort = javaPort;
    }

    public String getRequestPath(){
        return "http://" + getIp() + ":" + getServerPort();
    }

}
