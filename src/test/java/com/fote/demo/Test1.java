package com.fote.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.beans.msg.QQPrivateMsg;
import com.forte.component.forcoolqhttpapi.beans.send.SendPrivateMsg;
import com.forte.component.forcoolqhttpapi.server.SendJsonCreator;
import com.forte.component.forcoolqhttpapi.utils.OriginalDataUtil;
import com.forte.qqrobot.utils.proxyhelper.JSONParameterCreatorHelper;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test1 {

    public static void main(String[] args) throws Exception {
//        SendJsonCreator creator = JSONParameterCreatorHelper.toJsonParameterCreator(SendJsonCreator.class);
//
//        System.out.println(JSON.toJSONString(creator.setFriendAddRequest("flag", false,  "头衔")));
//        System.out.println(JSON.toJSONString(creator.setFriendAddRequest("flag",   "头衔")));


        SendPrivateMsg sendPrivateMsg = new SendPrivateMsg("1149159218", "哈哈哈哈");

        JSONObject json = JSONObject.parseObject(JSON.toJSONString(sendPrivateMsg));

        System.out.println(OriginalDataUtil.putOriginal(json).toJSONString());

        new QQPrivateMsg();

    }

}
