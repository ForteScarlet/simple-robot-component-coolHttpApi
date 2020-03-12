package com.fote.demo;

import com.forte.component.forcoolqhttpapi.CQHttpContext;
import com.forte.component.forcoolqhttpapi.CoolQHttpApplication;
import com.forte.qqrobot.ConfigurationProperty;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.SimpleRobotConfiguration;
import com.forte.qqrobot.bot.BotSender;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@SimpleRobotApplication(resources = "/conf.properties")
@SimpleRobotConfiguration({
        @ConfigurationProperty(key = "core.bots", value = ":http://127.0.0.1:5701"),
        @ConfigurationProperty(key = "core.enableServer", value = "false"),
        @ConfigurationProperty(key = "cqhttp.javaPort", value = "8877"),
        @ConfigurationProperty(key = "core.checkVersion", value = "false"),
})
public class Test1 {

    public static void main(String[] args) {
        // http://127.0.0.1:5700
        CoolQHttpApplication application = new CoolQHttpApplication();
        // 启动...
        CQHttpContext context = application.run(Test1.class, args);

        final BotSender sender = context.getBotManager().defaultBot().getSender();

        sender.SENDER.sendPrivateMsg("1149159218", "转瞬即逝..");

        System.exit(-1);

    }
}
