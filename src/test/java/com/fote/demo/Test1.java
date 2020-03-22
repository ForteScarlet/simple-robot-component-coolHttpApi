package com.fote.demo;

import com.forte.component.forcoolqhttpapi.CQHttpContext;
import com.forte.component.forcoolqhttpapi.CoolQHttpApplication;
import com.forte.component.forcoolqhttpapi.MsgOnManager;
import com.forte.component.forcoolqhttpapi.beans.result.QQFriendList;
import com.forte.qqrobot.ConfigurationProperty;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.SimpleRobotConfiguration;
import com.forte.qqrobot.beans.messages.result.FriendList;
import com.forte.qqrobot.factory.MsgGetTypeFactory;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.log.QQLogBack;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
// 读取配置文件
@SimpleRobotApplication(resources = "/conf.properties")
@SimpleRobotConfiguration({
        /*
            一些测试用的配置
         */
        @ConfigurationProperty(key = "core.bots", value = ":http://127.0.0.1:5701"),
        @ConfigurationProperty(key = "cqhttp.serverPath", value = "/coolq/listen"),
        @ConfigurationProperty(key = "cqhttp.javaPort", value = "8877"),
        @ConfigurationProperty(key = "core.checkVersion", value = "false"),
})
public class Test1 {

    public static void main(String[] args) throws Exception {
        // 注册一个胶叫做“MY_TYPE”的监听类型，类型为MyListen
        MsgGetTypeFactory.registerType("MY_TYPE", MyListen.class);
        // 注册一个CQ HTTP API的监听消息封装类类型，需要标注@MsgOn注解并继承BaseMsg类
        MsgOnManager.register(MyListen.class);

        /*
            监听类型工厂与其他工厂一样，基本上只兼容jdk8（出现过jdk11会报错的问题，其他版本未知
         */

        // 然后正常执行
        final CQHttpContext run = new CoolQHttpApplication().run(Test1.class, args);


    }
}
