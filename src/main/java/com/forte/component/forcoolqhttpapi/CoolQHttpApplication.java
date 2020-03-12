package com.forte.component.forcoolqhttpapi;

import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.beans.msg.*;
import com.forte.component.forcoolqhttpapi.server.*;
import com.forte.component.forcoolqhttpapi.utils.JSONDataUtil;
import com.forte.component.forcoolqhttpapi.utils.PostTypeUtils;
import com.forte.lang.Language;
import com.forte.plusutils.consoleplus.console.Colors;
import com.forte.qqrobot.*;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.bot.*;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.exception.*;
import com.forte.qqrobot.factory.MsgGetTypeFactory;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.log.QQLogBack;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.RootSenderList;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.collections.map.SingletonMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * CoolQ HTTP API 组件对接 启动器
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpApplication extends BaseApplication<
        CoolQHttpConfiguration,
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
     * 启动的服务
     */
    private CoolQHttpServer server;

    /** post_type 的键的值 */
    private static final String POST_TYPE_KEY_NAME = "post_type";

    /**
     * 开发者实现的资源初始化
     */
    @Override
    protected void resourceInit() {
        //初始化配置类实例
        getConf();
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
     * 资源初始化
     *
     * @param configuration 配置对象
     */
    @Override
    protected void resourceInit(CoolQHttpConfiguration configuration) {
        MsgOnManager.scan(CoolQHttpHandlerFactory.MSG_GET_PACK);
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
        } catch (RobotRunException e) {
            throw new BotVerifyException("failed", code, e.getMessage(), e);
        }
        // code不为null，验证code是否匹配
        if (code != null) {
            // code 不匹配
            if (!loginInfo.getBotCode().equals(code)) {
                throw new BotVerifyException("failed.mismatch", code, loginInfo);
            }
        }

        return loginInfo;

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
        return new CoolQHttpBotSender(msgGet, botManager);
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
        return new CoolQHttpBotSender(msgGet, botManager);
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
        return new CoolQHttpBotSender(msgGet, botManager);
    }

    /**
     * 根据{@link #getSender(MsgGet, BotManager)}, {@link #getSetter(MsgGet, BotManager)}, {@link #getGetter(MsgGet, BotManager)} 三个函数构建一个RootSenderList
     * 参数分别为一个BotManager和一个MsgGet对象
     * 如果组件不是分为三个部分而构建，则可以考虑重写此函数
     * 此函数最终会被送入组件实现的runServer中
     *
     * @return RootSenderList构建函数
     */
    @Override
    protected Function<MsgGet, RootSenderList> getRootSenderFunction(BotManager botManager) {
        return m -> new CoolQHttpBotSender(m, botManager);
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
        CoolQHttpConfiguration conf = getConf();

        // 构建默认的Bot送信器
        BotInfo defaultBot = conf.getDefaultBotInfo();

        BotInfo defaultBotInfo = botManager.getBot(defaultBot.getBotCode());
        CoolQHttpMsgSender coolQHttpMsgSender = new CoolQHttpMsgSender(defaultBotInfo);

        //初始化sender
        CoolQHttpResourceDispatchCenter.saveCoolQHttpMsgSender(coolQHttpMsgSender);
        msgSender = coolQHttpMsgSender;

        // 构建一个MsgSender
        return MsgSender.NoListenerMsgSender.build(msgSender, msgSender, msgSender, BotRuntime.getRuntime());
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
        return new CQHttpContext(
                (CoolQHttpMsgSender) defaultMsgSender.SENDER,
                (CoolQHttpMsgSender) defaultMsgSender.SENDER,
                (CoolQHttpMsgSender) defaultMsgSender.GETTER,
                manager, msgParser, processor, dependCenter);
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
        // 先转化为json格式
        JSONObject paramsJSON = JSONObject.parseObject(str);
        // 获取typeMap
        Map<PostType, Map<String, Class<? extends MsgGet>>> postTypeMap = MsgOnManager.getPostTypeMap();

        // 获取值
        Class<? extends MsgGet> type = getTypeByPostType(postTypeMap, paramsJSON);

        if(type != null){
            // 封装
            JSONObject json = JSONDataUtil.putObjOriginal(paramsJSON);
            return JSONObject.toJavaObject(json, type);
        }else{
            return null;
        }
    }

    /**
     * 根据获取到的参数类型转化为MsgGet对象
     * @param typeMap       类型映射map
     * @param jsonObject    解析后的jsonObject对象
     * @return
     */
    private static Class<? extends MsgGet> getTypeByPostType(Map<PostType, Map<String, Class<? extends MsgGet>>> typeMap, JSONObject jsonObject){
        // 获取PostType类型
        String postTypeValue = jsonObject.getString(POST_TYPE_KEY_NAME);

        PostType postType;
        try {
            postType = PostType.valueOf(postTypeValue);
        }catch (IllegalArgumentException e){
            return null;
        }

        // 获取对应的Map值
        Map<String, Class<? extends MsgGet>> stringClassMap = typeMap.get(postType);
        if(stringClassMap == null){
            return null;
        }
        // 获取键值
        String keyName = postType.keyName;
        // 获取此key的值
        String string = jsonObject.getString(keyName);
        return stringClassMap.get(string);
    }


    /**
     * 启动一个服务，这有可能是http或者是ws的监听服务
     * @param dependCenter   依赖中心
     * @param manager        监听管理器
     * @param processor      送信解析器
     * @param parser         字符串解析器
     * @return
     */
    @Override
    protected String runServer(DependCenter dependCenter, ListenerManager manager, MsgProcessor processor, MsgParser parser) {
        CoolQHttpConfiguration conf = getConf();

        String requestPath = conf.getServerPath();
        String encode = conf.getEncode();
        String[] method = conf.getMethod();

        // 响应器
        CoolQHttpHandler coolQHttpHandler = new CoolQHttpHandler(
                encode,
                method,
                manager,
                processor,
                parser
        );

        // 创建一个单值Map，增加一个监听映射
        Map<String, HttpHandler> map = new SingletonMap(requestPath, coolQHttpHandler);

        int javaPort = conf.getJavaPort();
        int backLog = conf.getBackLog();

        // 启动服务器
        try {
            // 赋值至成员变量
            server = CoolQHttpServer.startServer(
                    javaPort,
                    map,
                    backLog
            );
        } catch (IOException e) {
            throw new RobotRunException("serverStartFailed", e);
        }

        // 记录启动的服务
        dependCenter.loadIgnoreThrow(server);

        // show server info
        String listenUrl = "http://[::]:" + javaPort + requestPath;
        getLog().info("server.listenurl", listenUrl);

        return "CQ HTTP 组件监听服务器";
    }

    /**
     * 开发者实现的启动方法
     * v1.1.2-BETA后返回值修改为String，意义为启动结束后打印“启动成功”的时候使用的名字
     * 例如，返回值为“server”，则会输出“server”启动成功
     *
     * @param manager 监听管理器，用于分配获取到的消息
     */
//    @Override
    protected String start2(DependCenter dependCenter, ListenerManager manager) {
        CoolQHttpConfiguration conf = getConf();

        // 构建默认的Bot送信器
        BotInfo defaultBot = conf.getDefaultBotInfo();
        BotManager botManager = getBotManager();

        BotInfo defaultBotInfo = botManager.getBot(defaultBot.getBotCode());
        CoolQHttpMsgSender coolQHttpMsgSender = new CoolQHttpMsgSender(defaultBotInfo);

        //初始化sender
        CoolQHttpResourceDispatchCenter.saveCoolQHttpMsgSender(coolQHttpMsgSender);
        msgSender = coolQHttpMsgSender;


        String requestPath = conf.getServerPath();
        String encode = conf.getEncode();
        String[] method = conf.getMethod();


        MsgOnManager.scan(CoolQHttpHandlerFactory.MSG_GET_PACK);
        Map<PostType, Map<String, Class<? extends MsgGet>>> postTypeMap = MsgOnManager.getPostTypeMap();

        // 响应器
        CoolQHttpHandler coolQHttpHandler = new CoolQHttpHandler(
                encode,
                method,
                manager,
                null, null
        );

        // 创建一个单值Map，增加一个监听映射
        Map<String, HttpHandler> map = new SingletonMap(requestPath, coolQHttpHandler);

        int javaPort = conf.getJavaPort();
        int backLog = conf.getBackLog();

        // 启动服务器
        try {
            // 赋值至成员变量
            server = CoolQHttpServer.startServer(
                    javaPort,
                    map,
                    backLog

            );
        } catch (IOException e) {
            throw new RobotRunException("serverStartFailed", e);
        }

        // 记录启动的服务
        dependCenter.loadIgnoreThrow(server);

        // show server info
        String listenUrl = "http://[::]:" + javaPort + requestPath;

        BotInfo[] bots = botManager.bots();

        getLog().info("server.listenurl", listenUrl);
        getLog().info("server.method", Arrays.toString(method));
        for (BotInfo bot : bots) {
            getLog().info("server.sendurl", bot.getBotCode(), bot.getPath());
        }

        return "coolQ HTTP API server";
    }


    /**
     * 获取并展示登录的QQ的部分信息并在配置中记录此信息
     *
     * @param configuration 配置类
     */
    @Deprecated
    private void getAndShowQQInfo(CoolQHttpConfiguration configuration) {
        //获取登录的机器人的信息
        LoginQQInfo loginQQInfo = msgSender.getLoginQQInfo();
        if (loginQQInfo == null) {
            // 直接是个空，抛出异常
            throw new NullPointerException();
        }
        configuration.setLoginQQInfo(loginQQInfo);
        getLog().info("login.info.code", Colors.builder().add(loginQQInfo.getQQ(), Colors.FONT.YELLOW).build());
        getLog().info("login.info.nick", Colors.builder().add(loginQQInfo.getName(), Colors.FONT.YELLOW).build());
        getLog().info("login.info.level", Colors.builder().add(loginQQInfo.getLevel(), Colors.FONT.YELLOW).build());

    }

    /**
     * 根据验证前的botInfo构建一个验证后的botInfo并初始化其BotSender
     *
     * @param botInfo botInfo对象
     * @return 验证后的botInfo对象。与原来的BotInfo一致。如果验证失败则返回null
     */
    private BotInfo getLoginInfo(BotInfo botInfo) {
        // 构建送信器
        CoolQHttpMsgSender coolQHttpMsgSender = new CoolQHttpMsgSender(botInfo);
        // 尝试获取登录信息
        LoginInfo loginInfo = coolQHttpMsgSender.getLoginQQInfo();
        if (loginInfo == null) {
            throw new RobotRunException("login.bot.info.failed", botInfo.getBotCode(), botInfo.getPath());
        }
        // 验证后的botInfo
        BotInfo verifyBot = new BotInfoImpl(loginInfo.getCode(), botInfo.getPath(), loginInfo, new BotSender(coolQHttpMsgSender));

        return verifyBot;
    }


    /**
     * 开发者实现的获取Config对象的方法,对象请保证每次获取的时候都是唯一的
     */
    @Override
    protected CoolQHttpConfiguration getConfiguration() {
        CoolQHttpConfiguration coolQHttpConfiguration = CoolQHttpResourceDispatchCenter.getCoolQHttpConfiguration();
        if (coolQHttpConfiguration == null) {
            coolQHttpConfiguration = new CoolQHttpConfiguration();
            CoolQHttpResourceDispatchCenter.saveCoolQHttpConfiguration(coolQHttpConfiguration);
        }
        return coolQHttpConfiguration;
    }

    /**
     * interface {@link java.io.Closeable} method
     */
    @Override
    public void close() {
        if (server != null) {
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
