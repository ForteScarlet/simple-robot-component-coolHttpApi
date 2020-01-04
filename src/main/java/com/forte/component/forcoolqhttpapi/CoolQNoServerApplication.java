package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.beans.msg.Heartbeat;
import com.forte.component.forcoolqhttpapi.beans.msg.Lifecycle;
import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.qqrobot.BaseApplication;
import com.forte.qqrobot.anno.Version;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.exception.ConfigurationException;
import com.forte.qqrobot.factory.MsgGetTypeFactory;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.invoker.MsgReceiver;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.io.IOException;
import java.util.function.Function;

/**
 *
 * 无监听启动器
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQNoServerApplication extends BaseApplication<CoolQNoServerConfiguration, CoolQHttpAPI> {

    /**
     * 送信器
     */
    private CoolQHttpMsgSender msgSender;

    /**
     * 特殊API
     */
    private CoolQHttpAPI spAPI;

    /**
     * 配置对象
     */
    private CoolQNoServerConfiguration config;

    /**
     * 监听器管理中心
     */
    private ListenerManager manager;

    /**
     * 监听接收器
     */
    private MsgReceiver receiver;

    /**
     * 资源初始化
     */
    @Override
    protected void resourceInit(CoolQNoServerConfiguration config) {
    }

    /**
     * 开发者实现的资源初始化, 用户配置之前执行
     */
    @Override
    protected void resourceInit() {
        //初始化配置类实例
        config = new CoolQNoServerConfiguration();
        CoolQHttpResourceDispatchCenter.saveCoolQHttpConfiguration(config);
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

    @Override
    protected SenderSendList getSender() {
        return msgSender;
    }

    @Override
    protected SenderSetList getSetter() {
        return msgSender;
    }

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
        // 由于不需要启动监听服务，所以直接获取QQ信息，并将manager转化为msgOn
        // 获取QQ信息
        QQLog.info("尝试获取登录QQ信息...");
        try {
            getAndShowQQInfo(this.config);
        }catch (Exception e){
            QQLog.error("登录QQ信息获取失败！请确保已手动配置登录QQ信息, 或后续进行配置。", e);
        }

        this.manager = manager;

        return "no listen server coolQ HTTP API server";
    }

    /**
     * 获取监听接收者
     * 需要在启动之后获取
     */
    public MsgReceiver getMsgReceiver(){
        if(manager == null){
            throw new ConfigurationException("服务尚未启动");
        }else{
            if(receiver != null){
                return receiver;
            }else{
                receiver = new MsgReceiver() {
                    @Override
                    public ListenResult[] onMsg(MsgGet msgGet) {
                        return manager.onMsg(msgGet, msgSender);
                    }

                    @Override
                    public ListenResult[] onMsg(String msgGetValue, Function<String, MsgGet> format) {
                        return onMsg(format.apply(msgGetValue));
                    }
                };
                return receiver;
            }
        }
    }


    @Override
    protected CoolQNoServerConfiguration getConfiguration() {
        return config;
    }

    @Override
    public void close() {
        // 没啥可关的
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
}
