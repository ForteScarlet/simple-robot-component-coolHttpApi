package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.qqrobot.MsgParser;
import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.SimpleRobotContext;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.DependCenter;

/**
 * CQ HTTP 组件
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class CQHttpContext extends SimpleRobotContext<CoolQHttpMsgSender, CoolQHttpMsgSender, CoolQHttpMsgSender> {

    /**
     * 构造函数
     * @param sender       sender送信器
     * @param setter       setter送信器
     * @param getter       getter送信器
     * @param manager      监听函数管理器
     * @param msgParser    消息字符串转化器
     * @param processor    监听消息执行器
     * @param dependCenter
     */
    public CQHttpContext(CoolQHttpMsgSender sender, CoolQHttpMsgSender setter, CoolQHttpMsgSender getter, BotManager manager, MsgParser msgParser, MsgProcessor processor, DependCenter dependCenter) {
        super(sender, setter, getter, manager, msgParser, processor, dependCenter);
    }
}
