package com.fote.demo;

import com.forte.component.forcoolqhttpapi.*;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;

import java.util.Properties;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test1 implements CoolQHttpResourceApp {

    public static void main(String[] args) {
        CoolQHttpApplication application = new CoolQHttpApplication();
        // 启动...
        application.run(new Test1());
    }

    public String resourceName() {
        return "/conf.properties";
    }


    @Override
    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) {
        sender.SENDER.sendPrivateMsg("1149159218", "hello");
    }

    /**
     * 此方法将会在配置文件装配完成后执行.
     * 所以如果这个时候更改Properties是 没有用的~没有用的~
     *
     * @param args          properties配置内容
     * @param configuration 配置好的配置文件
     */
    @Override
    public void before(Properties args, CoolQHttpConfiguration configuration) {
    }
}
