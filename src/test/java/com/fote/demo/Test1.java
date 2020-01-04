package com.fote.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.beans.msg.MsgOn;
import com.forte.component.forcoolqhttpapi.beans.msg.PostType;
import com.forte.component.forcoolqhttpapi.beans.msg.QQPrivateMsg;
import com.forte.component.forcoolqhttpapi.beans.result.LoginInfo;
import com.forte.component.forcoolqhttpapi.beans.result.QQGroupInfo;
import com.forte.component.forcoolqhttpapi.beans.send.SendPrivateMsg;
import com.forte.component.forcoolqhttpapi.utils.JSONDataUtil;
import com.forte.component.forcoolqhttpapi.utils.PostTypeUtils;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.scanner.FileScanner;
import com.forte.qqrobot.utils.AnnotationUtils;
import com.forte.utils.reflect.EnumUtils;
import com.forte.utils.reflect.ProxyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test1 {

//    public static void main(String[] args) throws Exception {
//        String json = "{\"data\":{\"admin_count\":3,\"admins\":[{\"nickname\":\"丛雨\",\"role\":\"admin\",\"user_id\":272594304},{\"nickname\":\"老妖\",\"role\":\"owner\",\"user_id\":1571650839},{\"nickname\":\"不动行光\",\"role\":\"admin\",\"user_id\":1730707275}],\"category\":33,\"create_time\":1575104042,\"group_id\":976262575,\"group_name\":\"rebot测试群\",\"introduction\":\"\",\"max_admin_count\":10,\"max_member_count\":200,\"member_count\":6},\"retcode\":0,\"status\":\"ok\"}";
//
//        QQGroupInfo info = JSONObject.toJavaObject(JSONObject.parseObject(json).getJSONObject("data"), QQGroupInfo.class);
//
//        System.out.println(info.getOriginalData());
//        System.out.println(info.getOwner_id());
//        System.out.println(info.getOwnerQQ());
//        System.out.println(info.getOwner_id());
//
//    }

}
