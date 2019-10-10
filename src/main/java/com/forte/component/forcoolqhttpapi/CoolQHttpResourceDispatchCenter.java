package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;
import com.forte.component.forcoolqhttpapi.server.SendJsonCreator;
import com.forte.qqrobot.ResourceDispatchCenter;

/**
 * HTTP API使用的资源调度器
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/15 17:15
 * @since JDK1.8
 **/
public class CoolQHttpResourceDispatchCenter extends ResourceDispatchCenter {

    /**
     * 保存一个Http配置类
     * @param httpConfiguration Http配置类
     */
    static void saveCoolQHttpConfiguration(CoolQHttpConfiguration httpConfiguration){
        saveConfiguration(httpConfiguration);
    }

    /**
     * 记录一个送信器Json封装器
     * @param jsonCreator json封装器
     */
    static void saveSendJsonCreator(SendJsonCreator jsonCreator){
        save(jsonCreator);
    }

    /**
     * 保存一个送信器
     * @param msgSender 送信器
     */
    static void saveCoolQHttpMsgSender(CoolQHttpMsgSender msgSender){
        save(msgSender);
    }

    /**
     * 保存一个特殊API
     * @param api   api
     */
    static void saveCoolQHttpAPI(CoolQHttpAPI api){
        save(api);
    }

//    /**
//     * 保存一个QQJSONMsg生成器
//     * @param qqjsonMsgCreator QQJSONMsg生成器
//     */
//    static void saveQQJSONMsgCreator(QQJSONMsgCreator qqjsonMsgCreator){
//        save(qqjsonMsgCreator);
//    }

//    /**
//     * 保存一个QQHttpMsgSender(真实送信器)
//     * @param qqHttpMsgSender QQHttpMsgSender(真正的送信器)
//     */
//    static void saveQQHttpMsgSender(QQHttpMsgSender qqHttpMsgSender){
//        save(qqHttpMsgSender);
//    }

//    /**
//     * 保存一个HttpSender(送信器接口实现者)
//     * @param httpSender 送信器接口实现者
//     */
//    static void saveHttpSender(HttpSender httpSender){
//        save(httpSender);
//    }

    //**************** get ****************//


    /**
     * 获取一个HttpConfiguration单例对象
     * @return HttpConfiguration单例对象
     */
    public static CoolQHttpConfiguration getCoolQHttpConfiguration(){
        return get(CoolQHttpConfiguration.class);
    }

    /**
     * 获取一个送信器Json封装器
     */
    public static SendJsonCreator getSendJsonCreator(){
        return get(SendJsonCreator.class);
    }

    /**
     * 获取要给送信器
     */
    public static CoolQHttpMsgSender getCoolQHttpMsgSender(){
        return get(CoolQHttpMsgSender.class);
    }

    /**
     * 获取特殊API对象
     */
    public static CoolQHttpAPI getCoolQHttpAPI(){
        return get(CoolQHttpAPI.class);
    }

//    /**
//     * 获取一个QQJSONMsgCreator单例对象
//     * @return QQJSONMsgCreator单例对象
//     */
//    public static QQJSONMsgCreator getQQJSONMsgCreator(){
//        return get(QQJSONMsgCreator.class);
//    }
//
//    /**
//     * 获取一个QQHttpMsgSender单例对象
//     * @return QQHttpMsgSender单例对象
//     */
//    public static QQHttpMsgSender getQQHttpMsgSender(){
//        return get(QQHttpMsgSender.class);
//    }
//
//    /**
//     * 获取一个HttpSender单例对象
//     * @return HttpSender单例对象
//     */
//    public static HttpSender httpSender(){
//        return get(HttpSender.class);
//    }

}
