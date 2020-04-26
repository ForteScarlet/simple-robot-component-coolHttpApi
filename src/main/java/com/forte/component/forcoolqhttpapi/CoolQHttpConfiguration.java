package com.forte.component.forcoolqhttpapi;

import com.forte.config.Conf;
import com.forte.qqrobot.BaseConfiguration;
import com.forte.qqrobot.log.QQLog;

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
    @Conf("cqhttp.javaPort")
    private int javaPort = 15514;

    /**
     * @see #registerBot(String)
     */
    @Conf("cqhttp.serverPort")
    @Deprecated
    private int serverPort = 5700;

    /**
     * TCP连接最大并发数, 传 0 或负数表示使用默认值
     */
    @Conf("cqhttp.backLog")
    private int backLog = 0;

    /**
     * 监听请求地址，默认为一个斜杠
     */
    @Conf("cqhttp.serverPath")
    private String serverPath = "/";

    /**
     * 接收的请求方式，默认为 post
     */
    @Conf("cqhttp.method")
    private String[] method = {"post"};

    /**
     * 上报数据使用的access_token
     * 如果配置文件中填写了 access_token，则每次请求需要在请求头中加入验证头，如：
     *
     *  GET /send_private_msg?user_id=123456&message=hello HTTP/1.1
     * Authorization: Bearer kSLuTF2GC2Q4q4ugm3
     * Access token 也可以通过 query 参数 access_token 传入，以便在无法修改请求头的情况下使用，例如：
     */
    @Conf("cqhttp.accessToken")
    private String accessToken = null;

    /**
     * 接收数据的secret
     *
     * 如果 secret 配置项也不为空，则会在每次上报的请求头中加入 HMAC 签名，如：
     *
     * POST / HTTP/1.1
     * X-Signature: sha1=f9ddd4863ace61e64f462d41ca311e3d2c1176e2
     * X-Self-ID: 123456
     *
     * 签名以 secret 作为密钥，HTTP 正文作为消息，进行 HMAC SHA1 哈希，
     * 你的后端可以通过该哈希值来验证上报的数据确实来自 CQHTTP 插件。
     *
     */
    @Conf("cqhttp.secret")
    private String secret = null;


    public int getBackLog() {
        return backLog;
    }

    /**
     * 设置TCP连接最大并发数, 传 0 或负数表示使用默认值
     *
     * @param backLog TCP连接最大并发数
     */
    public CoolQHttpConfiguration setBackLog(int backLog) {
        this.backLog = backLog;
        return this;
    }

    @Deprecated
    public int getServerPort() {
        return serverPort;
    }

    /**
     * 默认值: 5700
     * 设置酷Q端插件监听请求的端口号，用于酷Q插件端接收Java端的请求 <br>
     * Java --[请求]--> CoolQ
     *
     * @param serverPort 酷Q端插件监听请求的端口号
     */
    @Deprecated
    public CoolQHttpConfiguration setServerPort(int serverPort) {
        QQLog.error("{0}", "setPort参数已经被弃用!");
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
     *
     * @param javaPort java端监听端口
     */
    public CoolQHttpConfiguration setJavaPort(int javaPort) {
        this.javaPort = javaPort;
        return this;
    }

    @Deprecated
    public String getRequestPath() {
        return "http://" + getIp() + ":" + getServerPort();
    }

    public String getServerPath() {
        return serverPath;
    }

    /**
     * Java端监听酷Q插件端的时候使用的统一后缀，默认就是'/', 也就是没有后缀
     * 大致感觉就是：<br>
     * <code>http://{java_ip}:{java_port}{server_path}</code>
     *
     * @param serverPath java端响应请求的时候的路径地址
     */
    public CoolQHttpConfiguration setServerPath(String serverPath) {
        this.serverPath = serverPath;
        return this;
    }

    public String[] getMethod() {
        return method;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * 请求方式，一般情况下不需要改变，使用默认值就行。
     * 默认值为post类型
     *
     * @param method 请求方式
     */
    public CoolQHttpConfiguration setMethod(String[] method) {
        this.method = method;
        return this;
    }
}
