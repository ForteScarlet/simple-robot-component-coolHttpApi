package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.beans.msg.Heartbeat;
import com.forte.component.forcoolqhttpapi.beans.msg.Lifecycle;
import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.qqrobot.BaseApplication;
import com.forte.qqrobot.anno.Version;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.exception.ConfigurationException;
import com.forte.qqrobot.exception.EnumInstantiationException;
import com.forte.qqrobot.exception.EnumInstantiationRequireException;
import com.forte.qqrobot.factory.MsgGetTypeFactory;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.invoker.MsgReceiver;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
        getConf();
//        config = new CoolQNoServerConfiguration();
//        CoolQHttpResourceDispatchCenter.saveCoolQHttpConfiguration(config);
        CoolQHttpResourceDispatchCenter.saveCoolQHttpMsgSender(msgSender);
        //初始化特殊API并提供真正的msgSender以初始化
        spAPI = new CoolQHttpAPI(msgSender);
        CoolQHttpResourceDispatchCenter.saveCoolQHttpAPI(spAPI);

        // 构建额外几种msgGet类型
        // 元事件 - 生命周期
        try {
            MsgGetTypeFactory.registerType(ExMsgGet.LIFECYCLE, Lifecycle.class);
            getLog().debug("enum.register", ExMsgGet.LIFECYCLE);
        } catch (EnumInstantiationRequireException | EnumInstantiationException e) {
            getLog().warning("enum.register.failed", ExMsgGet.LIFECYCLE);
            getLog().debug("enum.register.failed", e, ExMsgGet.LIFECYCLE);
        }
        // 元事件 - 心跳
        try {
            MsgGetTypeFactory.registerType(ExMsgGet.HEARTBEAT, Heartbeat.class);
            getLog().debug("enum.register", ExMsgGet.HEARTBEAT);
        } catch (EnumInstantiationRequireException | EnumInstantiationException e) {
            getLog().warning("enum.register.failed", ExMsgGet.HEARTBEAT);
            getLog().debug("enum.register.failed", e, ExMsgGet.HEARTBEAT);
        }
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

    /**
     * start之前，会先对账号进行验证。将会使用此方法对注册的bot账号信息进行验证
     *
     * @param confBotInfos
     */
    @Override
    protected BotInfo[] verifyBots(Map<String, List<BotInfo>> confBotInfos) {
        return new BotInfo[0];
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
        getLog().info("login.get");
        try {
            getAndShowQQInfo(this.config);
        }catch (Exception e){
            getLog().error("login.null", e);
        }

        //初始化送信器
        msgSender = new CoolQHttpMsgSender(getBotManager().defaultBot());

        this.manager = manager;

        return "no listen server coolQ HTTP API server";
    }

    /**
     * 获取监听接收者
     * 需要在启动之后获取
     */
    public MsgReceiver getMsgReceiver(){
        if(manager == null){
            throw new ConfigurationException("server has not start.");
        }else{
            return manager;
        }
    }


    /**
     * 开发者实现的获取Config对象的方法,对象请保证每次获取的时候都是唯一的
     */
    @Override
    protected CoolQNoServerConfiguration getConfiguration() {
        CoolQHttpConfiguration coolQHttpConfiguration = CoolQHttpResourceDispatchCenter.getCoolQHttpConfiguration();
        if(coolQHttpConfiguration == null){
            coolQHttpConfiguration = new CoolQNoServerConfiguration();
            CoolQHttpResourceDispatchCenter.saveCoolQHttpConfiguration(coolQHttpConfiguration);
        }
        return (CoolQNoServerConfiguration) coolQHttpConfiguration;
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

        getLog().info("login.info.code",  Colors.builder().add(loginQQInfo.getQQ(), Colors.FONT.YELLOW).build());
        getLog().info("login.info.nick",  Colors.builder().add(loginQQInfo.getName(), Colors.FONT.YELLOW).build());
        getLog().info("login.info.level", Colors.builder().add(loginQQInfo.getLevel(), Colors.FONT.YELLOW).build());

    }
}
