/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     CQHttpContext.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.qqrobot.MsgParser;
import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.SimpleRobotContext;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.DependCenter;

/**
 * CQ HTTP 组件
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
public class CQHttpContext extends SimpleRobotContext<CoolQHttpMsgSender, CoolQHttpMsgSender, CoolQHttpMsgSender, CoolQHttpConfiguration, CoolQHttpApplication> {

    /**
     * 构造函数
     *
     * @param sender       sender送信器
     * @param setter       setter送信器
     * @param getter       getter送信器
     * @param manager      监听函数管理器
     * @param msgParser    消息字符串转化器
     * @param processor    监听消息执行器
     * @param dependCenter
     */
    public CQHttpContext(CoolQHttpMsgSender sender,
                         CoolQHttpMsgSender setter,
                         CoolQHttpMsgSender getter,
                         BotManager manager,
                         MsgParser msgParser,
                         MsgProcessor processor,
                         DependCenter dependCenter,
                         CoolQHttpConfiguration configuration,
                         CoolQHttpApplication application) {
        super(sender, setter, getter, manager, msgParser, processor, dependCenter, configuration, application);
    }
}
