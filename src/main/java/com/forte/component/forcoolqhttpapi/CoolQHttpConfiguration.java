package com.forte.component.forcoolqhttpapi;

import com.forte.config.Conf;
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
    @Conf("simple.robot.conf.coolqhttpapi.javaPort")
    private int javaPort = 15514;

    /**
     * 酷Q端插件的端口，默认5700（插件默认就是5700）
     */
    @Conf("simple.robot.conf.coolqhttpapi.serverPort")
    private int serverPort = 5700;

    /**
     * TCP连接最大并发数, 传 0 或负数表示使用默认值
     */
    @Conf("simple.robot.conf.coolqhttpapi.backLog")
    private int backLog = 0;

    /** 监听请求地址，默认为一个斜杠 */
    @Conf("simple.robot.conf.coolqhttpapi.serverPath")
    private String serverPath = "/";

    /**
     * 接收的请求方式，默认为 post
     */
    @Conf("simple.robot.conf.coolqhttpapi.method")
        private String[] method = {"post"};


    /*
        TODO 似乎还有加密传输的方式，后期考虑整合一下
     */



    public int getBackLog() {
        return backLog;
    }

    /**
     * 设置TCP连接最大并发数, 传 0 或负数表示使用默认值
     * @param backLog TCP连接最大并发数
     */
    public CoolQHttpConfiguration setBackLog(int backLog) {
        this.backLog = backLog;
        return this;
    }

    public int getServerPort() {
        return serverPort;
    }

    /**
     * 默认值: 5700
     * 设置酷Q端插件监听请求的端口号，用于酷Q插件端接收Java端的请求 <br>
     * Java --[请求]--> CoolQ
     * @param serverPort    酷Q端插件监听请求的端口号
     */
    public CoolQHttpConfiguration setServerPort(int serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public int getJavaPort() {
        return javaPort;
    }

    /**
     * 默认值: 15514
     * 设置Java端监听酷Q消息监听数据的端口号，用于Java端接收酷Q插件端的请求 <br>
     * CoolQ --[请求]--> Java
     * @param javaPort java端监听端口
     */
    public CoolQHttpConfiguration setJavaPort(int javaPort) {
        this.javaPort = javaPort;
        return this;
    }

    public String getRequestPath(){
        return "http://" + getIp() + ":" + getServerPort();
    }

    public String getServerPath() {
        return serverPath;
    }

    /**
     * Java端监听酷Q插件端的时候使用的统一后缀，默认就是'/', 也就是没有后缀
     * 大致感觉就是：<br>
     * <code>http://{java_ip}:{java_port}{server_path}</code>
     * @param serverPath java端响应请求的时候的路径地址
     */
    public CoolQHttpConfiguration setServerPath(String serverPath) {
        this.serverPath = serverPath;
        return this;
    }

    public String[] getMethod() {
        return method;
    }

    /**
     * 请求方式，一般情况下不需要改变，使用默认值就行。
     * 默认值为post类型
     * @param method 请求方式
     */
    public CoolQHttpConfiguration setMethod(String[] method) {
        this.method = method;
        return this;
    }
}
