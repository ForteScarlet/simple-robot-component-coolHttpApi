package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.beans.msg.*;
import com.forte.component.forcoolqhttpapi.server.CoolQHttpHandler;
import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.component.forcoolqhttpapi.server.CoolQHttpServer;
import com.forte.component.forcoolqhttpapi.utils.PostTypeUtils;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.qqrobot.BaseApplication;
import com.forte.qqrobot.anno.factory.MsgGetTypeFactory;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.beans.types.ResultSelectType;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.log.QQLogBack;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.collections.map.SingletonMap;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * CoolQ HTTP API 组件对接 启动器
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpApplication extends BaseApplication<CoolQHttpConfiguration, CoolQHttpAPI> {

    /**
     * 送信器
     */
    private CoolQHttpMsgSender msgSender;

    /**
     * 特殊API
     */
    private CoolQHttpAPI spAPI;

    /**
     * 启动的服务
     */
    private CoolQHttpServer server;


    /**
     * 开发者实现的资源初始化
     */
    @Override
    protected void resourceInit() {
        //初始化配置类实例
        CoolQHttpResourceDispatchCenter.saveCoolQHttpConfiguration(new CoolQHttpConfiguration());
        //初始化送信器
        msgSender = new CoolQHttpMsgSender();
        CoolQHttpResourceDispatchCenter.saveCoolQHttpMsgSender(msgSender);
        //初始化特殊API并提供真正的msgSender以初始化
        spAPI = new CoolQHttpAPI(msgSender);
        CoolQHttpResourceDispatchCenter.saveCoolQHttpAPI(spAPI);

        // 构建额外几种msgGet类型
        // 元事件 - 生命周期
        MsgGetTypeFactory.registerType(ExMsgGet.LIFECYCLE, Lifecycle.class);
        // 元事件 - 心跳
        MsgGetTypeFactory.registerType(ExMsgGet.HEARTBEAT, Heartbeat.class);
    }

    /**
     * 资源初始化
     *
     * @param configuration 配置对象
     */
    @Override
    protected void resourceInit(CoolQHttpConfiguration configuration) {
    }

    /**
     * 获取消息发送接口, 将会在连接成功后使用
     */
    @Override
    protected SenderSendList getSender() {
        return msgSender;
    }

    /**
     * 获取事件设置接口, 将会在连接成功后使用
     */
    @Override
    protected SenderSetList getSetter() {
        return msgSender;
    }

    /**
     * 获取资源获取接口, 将会在连接成功后使用
     */
    @Override
    protected SenderGetList getGetter() {
        return msgSender;
    }

    @Override
    public CoolQHttpAPI getSpecialApi() {
        return spAPI;
    }

    /**
     * 开发者实现的启动方法
     * v1.1.2-BETA后返回值修改为String，意义为启动结束后打印“启动成功”的时候使用的名字
     * 例如，返回值为“server”，则会输出“server”启动成功
     *
     * @param manager 监听管理器，用于分配获取到的消息
     */
    @Override
    protected String start(DependCenter dependCenter, ListenerManager manager) {
        CoolQHttpConfiguration conf = getConfiguration();

        String requestPath = conf.getServerPath();
        String encode = conf.getEncode();
        String[] method = conf.getMethod();

        // 扫描并获取所有的监听对象，然后转化
        Set<Class<?>> msgOnClasses = new FileScanner()
                .find(
                        "com.forte.component.forcoolqhttpapi.beans.msg",
                        c -> c.getAnnotation(MsgOn.class) != null
                ).get();

        Map<PostType, Map<String, Class<? extends MsgGet>>> postTypeMap = PostTypeUtils.toTypeMap(msgOnClasses);

        // 响应器
        CoolQHttpHandler coolQHttpHandler = new CoolQHttpHandler(
                                                    encode,
                                                    method,
                                                    manager,
                                                    msgSender,
                                                    conf.getResultSelectType(),
                                                    postTypeMap
                                            );

        // 创建一个单值Map，增加一个监听映射
        Map<String, HttpHandler> map = new SingletonMap(requestPath, coolQHttpHandler);

        int javaPort = conf.getJavaPort();
        int backLog = conf.getBackLog();

        // 获取QQ信息
        QQLog.debug("尝试获取登录QQ信息...");
        try {
            getAndShowQQInfo(conf);
        }catch (Exception e){
            QQLog.error("登录QQ信息获取失败！请确保已手动配置登录QQ信息。", e);
        }

        // 启动服务器
        try {
            // 赋值至成员变量
            server = CoolQHttpServer.startServer(
                    javaPort,
                    map,
                    backLog

            );
        } catch (IOException e) {
            throw new RobotRuntimeException("监听服务启动失败！", e);
        }

        // 记录启动的服务
        dependCenter.loadIgnoreThrow(server);

        return "coolQ HTTP API server";
    }


    /**
     * 获取并展示登录的QQ的部分信息并在配置中记录此信息
     * @param configuration
     */
    private void getAndShowQQInfo(CoolQHttpConfiguration configuration){
        //获取登录的机器人的信息
        LoginQQInfo loginQQInfo = msgSender.getLoginQQInfo();
        configuration.setLoginQQInfo(loginQQInfo);

        QQLog.info(Colors.builder().add("QQ    : "+loginQQInfo.getQQ(), Colors.FONT.YELLOW).build());
        QQLog.info(Colors.builder().add("NICK  : "+loginQQInfo.getName(), Colors.FONT.YELLOW).build());
        QQLog.info(Colors.builder().add("LEVEL : "+loginQQInfo.getLevel(), Colors.FONT.YELLOW).build());

    }


    /**
     * 开发者实现的获取Config对象的方法,对象请保证每次获取的时候都是唯一的
     */
    @Override
    protected CoolQHttpConfiguration getConfiguration() {
        return CoolQHttpResourceDispatchCenter.getCoolQHttpConfiguration();
    }

    /**
     * interface {@link java.io.Closeable} method
     */
    @Override
    public void close() {
        if(server != null) {
            server.close();
        }
    }


    /**
     * 无参构造
     */
    public CoolQHttpApplication() {
    }

    /**
     * 日志拦截构造
     */
    public CoolQHttpApplication(QQLogBack qqLogBack) {
        super(qqLogBack);
    }
}
