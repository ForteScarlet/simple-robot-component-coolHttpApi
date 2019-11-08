package com.fote.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.beans.msg.MsgOn;
import com.forte.component.forcoolqhttpapi.beans.msg.PostType;
import com.forte.component.forcoolqhttpapi.beans.msg.QQPrivateMsg;
import com.forte.component.forcoolqhttpapi.beans.send.SendPrivateMsg;
import com.forte.component.forcoolqhttpapi.utils.JSONDataUtil;
import com.forte.component.forcoolqhttpapi.utils.PostTypeUtils;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.scanner.FileScanner;

import java.util.Map;
import java.util.Set;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test1 {

    public static void main(String[] args) throws Exception {

        Set<Class<?>> msgOnClasses = new FileScanner()
                .find(
                        "com.forte.component.forcoolqhttpapi.beans.msg",
                        c -> c.getAnnotation(MsgOn.class) != null
                ).get();

        Map<PostType, Map<String, Class<? extends MsgGet>>> postTypeMapMap = PostTypeUtils.toTypeMap(msgOnClasses);


        postTypeMapMap.forEach((k, v) -> {
            System.out.println(k);
            v.forEach((vk, vv) -> {
                System.out.println("\t" + k.keyName + "=" + vk + "\t:\t" + vv);
            });
        });


    }

}
