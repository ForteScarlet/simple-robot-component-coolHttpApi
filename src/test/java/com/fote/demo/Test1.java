package com.fote.demo;

import com.forte.component.forcoolqhttpapi.*;
import com.forte.qqrobot.BotRuntime;
import com.forte.qqrobot.ConfigurationProperty;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.SimpleRobotConfiguration;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.listener.invoker.AtDetection;
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
        application.run(Test1.class);

        BotManager botManager = BotRuntime.getRuntime().getBotManager();

        for (BotInfo bot : botManager.bots()) {
            System.out.println(bot);
        }

        System.out.println("动态注册...");
        botManager.registerBot("http://127.0.0.1:5700");

        for (BotInfo bot : botManager.bots()) {
            System.out.println(bot);
        }

        botManager.getBot("2240189254").getSender().SENDER.sendPrivateMsg("1149159218", "我被注册啦！");


        System.exit(-1);
    }

    public String resourceName() {

        return "/conf.properties";
    }


//    @Override
//    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) { }
//
//    /**
//     * 此方法将会在配置文件装配完成后执行.
//     * 所以如果这个时候更改Properties是 没有用的~没有用的~
//     *
//     * @param args          properties配置内容
//     * @param configuration 配置好的配置文件
//     */
//    @Override
//    public void before(Properties args, CoolQHttpConfiguration configuration) {
//        configuration.registerBot("http://47.100.38.59:8877");
//    }
}
