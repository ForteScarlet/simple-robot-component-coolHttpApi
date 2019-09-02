package com.fote.demo;

import com.alibaba.fastjson.JSON;
import com.forte.component.forcoolqhttpapi.utils.SendJsonCreator;
import com.forte.qqrobot.utils.proxyhelper.JSONParameterCreatorHelper;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test1 {

    public static void main(String[] args) throws Exception {
        SendJsonCreator creator = JSONParameterCreatorHelper.toJsonParameterCreator(SendJsonCreator.class);

        System.out.println(JSON.toJSONString(creator.setFriendAddRequest("flag", false,  "头衔")));
        System.out.println(JSON.toJSONString(creator.setFriendAddRequest("flag",   "头衔")));



    }

}
