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
        目前没有进行多账号登录的打算，于是干脆全部使用静态字段 x

        不太行，还是抽空全都换回非静态比较靠谱
     */


    /**
     * 端口, 默认为15514
     */
    private static int javaPort = 15514;

    /**
     * 酷Q端插件的端口，默认5700（插件默认就是5700，不开启多账号模式的话。）
     */
    private static int serverPort = 5700;

    /**
     * TCP连接最大并发数, 传 0 或负数表示使用默认值
     */
    private static int backLog = 0;

    /** 监听请求地址，默认为一个斜杠 */
    private static String serverPath = "/";

    /**
     * 接收的请求方式，默认为 post
     */
    private String[] method = {"post"};


    /*
        TODO 似乎还有加密传输的方式，后期考虑整合一下
     */



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

    public static String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        CoolQHttpConfiguration.serverPath = serverPath;
    }

    public String[] getMethod() {
        return method;
    }

    public void setMethod(String[] method) {
        this.method = method;
    }
}
