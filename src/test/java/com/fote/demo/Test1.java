package com.fote.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.beans.msg.MsgOn;
import com.forte.component.forcoolqhttpapi.beans.msg.PostType;
import com.forte.component.forcoolqhttpapi.beans.msg.QQPrivateMsg;
import com.forte.component.forcoolqhttpapi.beans.result.LoginInfo;
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
@MsgOn(type = PostType.notice, messageType = "unknown")
public class Test1 {

    public static void main(String[] args) throws Exception {




    }

}
