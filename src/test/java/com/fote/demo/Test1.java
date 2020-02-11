package com.fote.demo;

import com.forte.component.forcoolqhttpapi.*;
import com.forte.qqrobot.BaseConfiguration;
import com.forte.qqrobot.beans.messages.result.FriendList;
import com.forte.qqrobot.beans.messages.result.inner.Friend;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test1 implements CoolQNoServerResourceApp {

    public static void main(String[] args) {
        CoolQNoServerApplication application = new CoolQNoServerApplication();
        // 启动...
        application.run(new Test1());
    }

    public String resourceName() {
        return "/config.properties";
    }

    @Override
    public InputStream getStream() {
        InputStream stream = this.getClass().getResourceAsStream(resourceName());
        return Objects.requireNonNull(stream, "未读取到配置文件 : resource inputstream is null.");
    }

    @Override
    public void before(Properties args, CoolQNoServerConfiguration configuration) {

    }

    @Override
    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) {

        sender.SENDER.sendPrivateMsg("1149159218", "hello");

        System.out.println(sender.GETTER.getAuthInfo());

        sender.SETTER.setGroupBan("581250423", "1149159218", 60);

        System.exit(-1);

    }
}
