package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.qqrobot.BaseApplication;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.log.QQLogBack;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.io.IOException;

/**
 *
 * CoolQ HTTP API 组件对接 启动器
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpApplication extends BaseApplication<CoolQHttpConfiguration, CoolQHttpAPI> {

    /** 送信器 */
    private CoolQHttpMsgSender msgSender;

    /** 特殊API */
    private CoolQHttpAPI spAPI;

    /**
     * 开发者实现的资源初始化
     */
//    @Override
    protected void resourceInit() {
        //初始化配置类实例
        CoolQHttpResourceDispatchCenter.saveCoolQHttpConfiguration(new CoolQHttpConfiguration());
        //初始化送信器
        msgSender = new CoolQHttpMsgSender();
        CoolQHttpResourceDispatchCenter.saveCoolQHttpMsgSender(msgSender);
        //初始化特殊API
        spAPI = new CoolQHttpAPI();
        CoolQHttpResourceDispatchCenter.saveCoolQHttpAPI(spAPI);
    }

    /**
     * 资源初始化
     * @param configuration 配置对象
     */
    @Override
    protected void resourceInit(CoolQHttpConfiguration configuration) {
        resourceInit();
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
    protected CoolQHttpAPI getSpecialApi() {
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
    protected String start(ListenerManager manager) {
        //TODO 启动服务


        return "coolQ HTTP API server";
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
        // TODO close()
    }


    /**
     * 无参构造
     */
    public CoolQHttpApplication() { }

    /**
     * 日志拦截构造
     */
    public CoolQHttpApplication(QQLogBack qqLogBack) {
        super(qqLogBack);
    }
}
