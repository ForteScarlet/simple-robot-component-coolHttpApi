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

    public static void main(String[] args) throws Exception {
//        System.out.println("run...");
        CoolQNoServerApplication application = new CoolQNoServerApplication();
        // 启动...
        application.run(new Test1());


//        TestPrivateMsg msg = new TestPrivateMsg();
//        application.getMsgReceiver().onMsg(msg, null, null, null);

    }

    public String resourceName() {
        return "/conf.properties";
    }

    @Override
    public InputStream getStream() {
        InputStream stream = this.getClass().getResourceAsStream(resourceName());
        return Objects.requireNonNull(stream, "未读取到配置文件 : resource inputstream is null.");
    }

    @Override
    public void before(Properties args, CoolQNoServerConfiguration configuration) {
        System.out.println(args.getProperty("simple.robot.conf.cqPath"));
        System.out.println(configuration.getCqPath());
    }

    @Override
    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) {
        FriendList friendList = sender.GETTER.getFriendList();

        System.out.println(friendList);
        System.out.println(friendList.getOriginalData());

        Map<String, Friend[]> fl = friendList.getFriendList();


        fl.forEach((k, v) -> {
            System.out.println("k: " + k);
            for (Friend f : v) {
                System.out.println(f);
            }
        });

    }
}
