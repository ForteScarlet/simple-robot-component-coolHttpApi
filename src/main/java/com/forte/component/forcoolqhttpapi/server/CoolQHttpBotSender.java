package com.forte.component.forcoolqhttpapi.server;

import com.forte.qqrobot.beans.messages.ThisCodeAble;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.exception.RobotApiException;

/**
 * botSender, 用于通过内建一个thisCode获取器来实现动态变更Sender内部
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class CoolQHttpBotSender extends CoolQHttpMsgSender {

    private ThisCodeAble thisCodeAble;
    private BotManager botManager;

    public CoolQHttpBotSender(ThisCodeAble thisCodeAble, BotManager manager) {
        super(null);
        this.thisCodeAble = thisCodeAble;
        this.botManager = manager;
    }

    @Override
    protected BotInfo getBotInfo() {
        // 根据当前的botCode获取bot信息
        String thisCode = thisCodeAble.getThisCode();
        if (thisCode == null) {
            BotInfo botInfo = botManager.defaultBot();
            if(botInfo == null){
                throw new RobotApiException("noDefault");
            }
            return botInfo;
        } else {
            return botManager.getBot(thisCode);
        }
    }
}
