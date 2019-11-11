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

    /**
     * 设置TCP连接最大并发数, 传 0 或负数表示使用默认值
     * @param backLog TCP连接最大并发数
     */
    public void setBackLog(int backLog) {
        CoolQHttpConfiguration.backLog = backLog;
    }

    public static int getServerPort() {
        return serverPort;
    }

    /**
     * 默认值: 5700
     * 设置酷Q端插件监听请求的端口号，用于酷Q插件端接收Java端的请求 <br>
     * Java --[请求]--> CoolQ
     * @param serverPort    酷Q端插件监听请求的端口号
     */
    public void setServerPort(int serverPort) {
        CoolQHttpConfiguration.serverPort = serverPort;
    }

    public static int getJavaPort() {
        return javaPort;
    }

    /**
     * 默认值: 15514
     * 设置Java端监听酷Q消息监听数据的端口号，用于Java端接收酷Q插件端的请求 <br>
     * CoolQ --[请求]--> Java
     * @param javaPort java端监听端口
     */
    public void setJavaPort(int javaPort) {
        CoolQHttpConfiguration.javaPort = javaPort;
    }

    public String getRequestPath(){
        return "http://" + getIp() + ":" + getServerPort();
    }

    public static String getServerPath() {
        return serverPath;
    }

    /**
     * Java端监听酷Q插件端的时候使用的统一后缀，默认就是'/', 也就是没有后缀
     * 大致感觉就是：<br>
     * <code>http://{java_ip}:{java_port}{server_path}</code>
     * @param serverPath java端响应请求的时候的路径地址
     */
    public void setServerPath(String serverPath) {
        CoolQHttpConfiguration.serverPath = serverPath;
    }

    public String[] getMethod() {
        return method;
    }

    /**
     * 请求方式，一般情况下不需要改变，使用默认值就行。
     * 默认值为post类型
     * @param method 请求方式
     */
    public void setMethod(String[] method) {
        this.method = method;
    }
}
