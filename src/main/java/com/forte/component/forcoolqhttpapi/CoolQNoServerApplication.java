package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.beans.msg.Heartbeat;
import com.forte.component.forcoolqhttpapi.beans.msg.Lifecycle;
import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.qqrobot.BaseApplication;
import com.forte.qqrobot.MsgParser;
import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.anno.Version;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.bot.*;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.exception.*;
import com.forte.qqrobot.factory.MsgGetTypeFactory;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.invoker.MsgReceiver;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.RootSenderList;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 无服务启动现在依赖于核心中的 {@code core.enableServer} 配置来控制
 **/
@Deprecated
public class CoolQNoServerApplication extends BaseApplication<
        CoolQNoServerConfiguration,
        CoolQHttpMsgSender,
        CoolQHttpMsgSender,
        CoolQHttpMsgSender,
        CQHttpContext
        > {

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

    /**
     * 提供一个msgGet，将其转化为SendList
     *
     * @param msgGet     msgGet
     * @param botManager
     * @return {@link SenderSendList}
     */
    @Override
    protected CoolQHttpMsgSender getSender(MsgGet msgGet, BotManager botManager) {
        return null;
    }

    /**
     * 提供一个msgGet，将其转化为SetList
     *
     * @param msgGet     msgGet
     * @param botManager
     * @return {@link SenderSetList}
     */
    @Override
    protected CoolQHttpMsgSender getSetter(MsgGet msgGet, BotManager botManager) {
        return null;
    }

    /**
     * 提供一个msgGet，将其转化为GetList
     *
     * @param msgGet     msgGet
     * @param botManager
     * @return {@link SenderGetList}
     */
    @Override
    protected CoolQHttpMsgSender getGetter(MsgGet msgGet, BotManager botManager) {
        return null;
    }

    /**
     * 获取一个组件专属的SimpleRobotContext对象
     *
     * @param defaultMsgSender 函数{@link #getDefaultSender(DependCenter, ListenerManager, BotManager)}的最终返回值
     * @param manager          botManager对象
     * @param msgParser        消息字符串转化函数
     * @param processor        消息处理器
     * @param dependCenter     依赖中心
     * @return 组件的Context对象实例
     */
    @Override
    protected CQHttpContext getComponentContext(MsgSender defaultMsgSender, BotManager manager, MsgParser msgParser, MsgProcessor processor, DependCenter dependCenter) {
        return null;
    }

    /**
     * <pre> start之前，会先对账号进行验证。将会使用此方法对注册的bot账号信息进行验证。
     * <pre> 鉴于机制的变更，最好在bot初始化的时候便将每个bot所对应的sender初始化结束。
     * <pre> 此验证函数后续会被注入至BotManager对象中用于动态验证。
     * <pre> 推荐在验证失败的时候抛出异常。
     * @param code 用户账号，可能为null
     * @param info 用于验证的bot，一般来讲应当至少存在一个path
     */
    @Override
    protected BotInfo verifyBot(String code, BotInfo info) {
        // 验证
        BotInfo loginInfo;
        try {
            loginInfo = this.getLoginInfo(info);
        }catch (RobotRunException e){
            throw new BotVerifyException("failed", code, e.getMessage(), e);
        }
        // code不为null，验证code是否匹配
        if(code != null){
            // code 不匹配
            if(!loginInfo.getBotCode().equals(code)){
                throw new BotVerifyException("failed.mismatch", code, loginInfo);
            }
        }

        return loginInfo;
    }

    /**
     * 根据验证前的botInfo构建一个验证后的botInfo并初始化其BotSender
     * @param botInfo botInfo对象
     * @return 验证后的botInfo对象。与原来的BotInfo一致。如果验证失败则返回null
     */
    private BotInfo getLoginInfo(BotInfo botInfo){
        // 构建送信器
        CoolQHttpMsgSender coolQHttpMsgSender = new CoolQHttpMsgSender(botInfo);
        // 尝试获取登录信息
        LoginInfo loginInfo = coolQHttpMsgSender.getLoginQQInfo();
        if(loginInfo == null){
            throw new RobotRunException("login.bot.info.failed", botInfo.getBotCode(), botInfo.getPath());
        }
        // 验证后的botInfo
        BotInfo verifyBot = new BotInfoImpl(loginInfo.getCode(), botInfo.getPath(), loginInfo, new BotSender(coolQHttpMsgSender));

        return verifyBot;
    }


    @Override
    public CoolQHttpAPI getSpecialApi() {
        return spAPI;
    }

    /**
     * 获取一个不使用在监听函数中的默认送信器
     *
     * @param dependCenter 依赖中心
     * @param manager      监听器管理中心
     * @param botManager   bot管理中心
     * @return
     */
    @Override
    protected MsgSender getDefaultSender(DependCenter dependCenter, ListenerManager manager, BotManager botManager) {
        return null;
    }

    /**
     * 启动一个服务，这有可能是http或者是ws的监听服务
     *
     * @param dependCenter 依赖中心
     * @param manager      监听管理器
     * @param msgProcessor 送信解析器
     * @param msgParser
     * @return
     */
    @Override
    protected String runServer(DependCenter dependCenter, ListenerManager manager, MsgProcessor msgProcessor, MsgParser msgParser) {
        return null;
    }

    /**
     * 字符串转化为MsgGet的方法，最终会被转化为{@link MsgParser}函数，
     * 会作为参数传入{@link #runServer(DependCenter, ListenerManager, MsgProcessor, MsgParser)}, 也会封装进{@link SimpleRobotContext}中
     *
     * @param str
     * @return
     */
    @Override
    protected MsgGet msgParse(String str) {
        return null;
    }


    /**
     * 开发者实现的启动方法
     * v1.1.2-BETA后返回值修改为String，意义为启动结束后打印“启动成功”的时候使用的名字
     * 例如，返回值为“server”，则会输出“server”启动成功
     *
     * @param manager 监听管理器，用于分配获取到的消息
     */
    protected String start2(DependCenter dependCenter, ListenerManager manager) {
        CoolQHttpConfiguration conf = getConf();
        // 由于不需要启动监听服务，所以直接获取QQ信息，并将manager转化为msgOn
        // 构建默认的Bot送信器
        BotInfo defaultBot = conf.getDefaultBotInfo();
        BotManager botManager = getBotManager();

        BotInfo defaultBotInfo = botManager.getBot(defaultBot.getBotCode());
        CoolQHttpMsgSender coolQHttpMsgSender = new CoolQHttpMsgSender(defaultBotInfo);

        //初始化sender
        CoolQHttpResourceDispatchCenter.saveCoolQHttpMsgSender(coolQHttpMsgSender);
        msgSender = coolQHttpMsgSender;

        //初始化送信器
//        msgSender = new CoolQHttpMsgSender(getBotManager().defaultBot());
//        this.manager = manager;

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
