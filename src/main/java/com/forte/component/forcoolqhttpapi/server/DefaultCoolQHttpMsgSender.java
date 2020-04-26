package com.forte.component.forcoolqhttpapi.server;

import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.sender.MsgSender;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DefaultCoolQHttpMsgSender extends CoolQHttpMsgSender {
    /**
     * 构造
     * 内部需要一个BotManager对象与一个ThisCodeAble对象
     *
     * @param botInfo
     */
    public DefaultCoolQHttpMsgSender(MsgSender msgSender, BotInfo botInfo) {
        super(botInfo);


    }
}
