package com.fote.demo;

import com.forte.component.forcoolqhttpapi.CQHttpContext;
import com.forte.component.forcoolqhttpapi.CoolQHttpApplication;
import com.forte.qqrobot.ConfigurationProperty;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.SimpleRobotConfiguration;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.bot.BotSender;
import com.forte.qqrobot.exception.EnumInstantiationException;
import com.forte.qqrobot.exception.EnumInstantiationRequireException;
import com.forte.qqrobot.factory.KeywordMatchTypeFactory;

import java.util.regex.Pattern;

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

    public static void main(String[] args)  {
        final CQHttpContext run = new CoolQHttpApplication().run(Test1.class, args);



    }
}
