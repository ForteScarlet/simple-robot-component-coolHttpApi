package com.fote.demo;

import com.forte.component.forcoolqhttpapi.*;
import com.forte.component.forcoolqhttpapi.beans.msg.QQGroupMsg;
import com.forte.component.forcoolqhttpapi.beans.result.QQGroupInfo;
import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.qqrobot.BotRuntime;
import com.forte.qqrobot.ConfigurationProperty;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.SimpleRobotConfiguration;
import com.forte.qqrobot.beans.messages.result.AuthInfo;
import com.forte.qqrobot.beans.messages.result.GroupNoteList;
import com.forte.qqrobot.beans.messages.result.inner.GroupNote;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.bot.BotSender;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.listener.Filterable;
import com.forte.qqrobot.listener.invoker.AtDetection;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.sender.senderlist.SenderSetList;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@SimpleRobotApplication(resources = "/conf.properties")
@SimpleRobotConfiguration({
        @ConfigurationProperty(key = "core.bots", value = ":http://47.100.38.59:8877"),
        @ConfigurationProperty(key = "core.checkVersion", value = "false"),
})
public class Test1 implements CoolQHttpApp {

    public static void main(String[] args) throws NoSuchMethodException {
        // http://127.0.0.1:5700
        CoolQHttpApplication application = new CoolQHttpApplication();
        // 启动...
        application.run(Test1.class, args);


        BotManager botManager = BotRuntime.getRuntime().getBotManager();
        BotSender sender = botManager.getBot("2257290268").getSender();

        CoolQHttpMsgSender s = (CoolQHttpMsgSender) sender.SENDER;

        s.sendGroupNotice("699465940", "这是个标题", "这是正文第一行\n这是正文第二行", false, false, false);



    }

    @Override
    public void before(CoolQHttpConfiguration configuration) {
        System.out.println("before!!!!!!!!!!!!!!");
    }

    @Override
    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) {
        System.out.println("after!!!!!!!!!!!!!!!!!!");
    }
}
