/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     CoolQHttpServer.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * CoolQ监听消息所需Http服务器
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpServer implements Closeable {
    /*
        上报方式
        当配置文件中 post_url 配置项不为空时（无论 use_http 是什么），会将 酷Q 收到的事件通过 HTTP POST 上报，
        数据以 JSON 格式表示。上报的请求头中会包含一个 X-Self-ID 头来表示当前正在上报的机器人 QQ 号，如：
        POST / HTTP/1.1
        X-Self-ID: 123456

        如果 secret 配置项也不为空，则会在每次上报的请求头中加入 HMAC 签名，如：
        POST / HTTP/1.1
        X-Signature: sha1=f9ddd4863ace61e64f462d41ca311e3d2c1176e2
        X-Self-ID: 123456
        签名以 secret 作为密钥，HTTP 正文作为消息，进行 HMAC SHA1 哈希，
        你的后端可以通过该哈希值来验证上报的数据确实来自 HTTP API 插件。HMAC 介绍见 密钥散列消息认证码。


        每个事件都有一个共同的支持的操作，即 block 字段，数据类型为 boolean，true
        表示拦截事件（不再让后面的插件处理），否则表示忽略（不拦截），例如：

        {
            "block": true
        }
     */

    /**
     * 启动JavaServer监听服务
     *
     * @param port       java服务端口
     * @param listenPath 监听的地址列表
     * @param backlog    TCP连接最大并发数, 传 0 或负数表示使用默认值
     */
    public static CoolQHttpServer startServer(int port, Map<String, HttpHandler> listenPath, int backlog) throws IOException {
        HttpServerProvider provider = HttpServerProvider.provider();
        HttpServer httpserver = provider.createHttpServer(new InetSocketAddress(port), backlog);
        //注册监听地址
        listenPath.forEach(httpserver::createContext);
        //启动服务
        httpserver.start();
        return new CoolQHttpServer(httpserver);
    }


    /**
     * 真正的HttpServer对象
     */
    private HttpServer httpServer;

    /**
     * 构造
     */
    private CoolQHttpServer(HttpServer httpserver) {
        this.httpServer = httpserver;
    }

    @Override
    public void close() {
        // 终止服务
        httpServer.stop(0);
    }

}
