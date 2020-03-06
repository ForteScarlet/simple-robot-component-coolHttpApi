package com.fote.demo;

import com.forte.component.forcoolqhttpapi.*;
import com.forte.qqrobot.BotRuntime;
import com.forte.qqrobot.ConfigurationProperty;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.SimpleRobotConfiguration;
import com.forte.qqrobot.beans.messages.result.GroupNoteList;
import com.forte.qqrobot.beans.messages.result.inner.GroupNote;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.bot.BotSender;
import com.forte.qqrobot.listener.invoker.AtDetection;
import com.forte.qqrobot.listener.invoker.ListenerFilter;
import com.forte.qqrobot.sender.MsgSender;
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
        @ConfigurationProperty(key = "", value = ""),
        @ConfigurationProperty(key = "", value = ""),
        @ConfigurationProperty(key = "", value = ""),
})
public class Test1 {

    public static void main(String[] args) throws NoSuchMethodException {
        // http://127.0.0.1:5700
        CoolQHttpApplication application = new CoolQHttpApplication();
        // 启动...
        application.run(Test1.class, args);


        BotManager botManager = BotRuntime.getRuntime().getBotManager();
        BotSender sender = botManager.getBot("2257290268").getSender();

        GroupNoteList groupNoteList = sender.GETTER.getGroupNoteList("782930037");

        System.out.println(groupNoteList);

        groupNoteList.forEach(System.out::println);

        System.exit(-1);
    }

}
