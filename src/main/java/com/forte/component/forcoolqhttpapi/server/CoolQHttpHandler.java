package com.forte.component.forcoolqhttpapi.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.beans.msg.PostType;
import com.forte.component.forcoolqhttpapi.utils.JSONDataUtil;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.types.ResultSelectType;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.log.QQLog;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 *
 * http数据处理对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpHandler implements HttpHandler {

    /** 编码格式 */
    private String encoding;

    /** 可以接受的请求方式 */
    private String[] methods;

    /** 监听消息管理器 */
    private ListenerManager manager;

    /** 送信器 */
    private CoolQHttpMsgSender httpSender;

    /**
     * 根据postType类型和type类型来获取真正对应的MsgGet数据类型
     */
    private Map<PostType, Map<String, Class<? extends MsgGet>>> typeMap;

    /**
     * 用于判断请求类型是否正确的函数
     */
    private Predicate<String> isMethod;

    /** 默认返回值 */
    private static final String DEFAULT_RESULT = "{}";

    /** post_type 的键的值 */
    private static final String POST_TYPE_KEY_NAME = "post_type";

    /** 返回值结果的筛选方案 */
    private final ResultSelectType resultSelectType ;

    /**
     * 构造，提供所需参数
     * @param encoding  编码格式，一般来讲就是固定为utf-8了
     * @param methods   请求方式，一般来讲就是post了
     * @param manager   监听消息管理器
     * @param httpSender    送信器
     * @param typeMap   所有监听中的posttype类型与根据值对应的封装类类型
     */
    public CoolQHttpHandler(String encoding,
                            String[] methods,
                            ListenerManager manager,
                            CoolQHttpMsgSender httpSender,
                            ResultSelectType resultSelectType,
                            Map<PostType, Map<String, Class<? extends MsgGet>>> typeMap){
        this.encoding = encoding;
        this.methods = methods;
        this.manager = manager;
        this.resultSelectType = resultSelectType;
        this.typeMap = typeMap;
        this.httpSender = httpSender;
        if(methods == null || methods.length == 0){
            // 如果参数中methods没东西或者为空，默认使用post类型
            this.isMethod = "post"::equalsIgnoreCase;
        }else if(methods.length == 1){
            String m = methods[0];
            this.isMethod = me -> (me != null) && (me.equalsIgnoreCase(m));
        }else{
            // 有多个
            this.isMethod = me -> {
                if(me == null) {
                    return false;
                }
                for (String method : methods) {
                    if(me.equalsIgnoreCase(method)){
                        return true;
                    }
                }
                return false;
            };

        }


    }

    @Override
    public void handle(HttpExchange httpExchange) {
        //使用线程异步接收消息
        ResourceDispatchCenter.getThreadPool().execute(() -> doHandle(httpExchange));


    }


    /**
     * 新线程中进行操作
     */
    public void doHandle(HttpExchange httpExchange){

        try {
            //获得表单提交数据
            //判断请求方式
            String method = httpExchange.getRequestMethod();
            // 如果请求方式正确，进行下一步
            if(isMethod.test(method)){
                //获取接收到的参数
                InputStream requestBody = httpExchange.getRequestBody();
                //编码转义  貌似不再需要编码转义
                String paramsUrl = IOUtils.toString(requestBody, this.encoding);
                QQLog.debug("http request body: " + paramsUrl);
                //                try {
//                    params = URLDecoder.decode(paramsUrl, this.encoding);
//                }catch (Exception e){
//                    QQLog.warning("HTTP请求体解码异常, 将使用非解码值");
//                    params = paramsUrl;
//                }

                // 先转化为json格式
                JSONObject paramsJSON = JSONObject.parseObject(paramsUrl);

                // 获取值
                Class<? extends MsgGet> type = getTypeByPostType(typeMap, paramsJSON);

                // 如果有对应的值，将JSON转化为携带原始数据的json
                if(type != null){
                    // 封装
                    JSONObject json = JSONDataUtil.putObjOriginal(paramsJSON);
                    MsgGet msgGet = JSONObject.toJavaObject(json, type);

                    ListenResult[] results;

                    // 消息处理
                    if(msgGet != null){
                        results = manager.onMsg(msgGet, httpSender);
                    }else{
                        results = new ListenResult[0];
                    }

                    // 过滤返回值结果
                    ListenResult<?> result = resultSelectType.filter(results);

                    // 响应数据
                    // 设置响应code和内容长度
                    httpExchange.sendResponseHeaders(200, 0);

                    //获取响应输出流
                    OutputStream out = httpExchange.getResponseBody();

                    String resultBody = getResultBody(result);

                    // 响应信息
                    IOUtils.write(resultBody, out, this.encoding);
                }
            }else{
                // 不是支持的请求方式，返回405类型
                try {
                    // 设置响应code和内容长度
                    httpExchange.sendResponseHeaders(405, 0);
                    OutputStream out = httpExchange.getResponseBody();
                    // 响应信息
                    IOUtils.write("no method type", out, this.encoding);
                } catch (IOException e1) {
                    QQLog.error("消息响应异常", e1);
                }
            }
        }catch (Exception e){
            QQLog.error("[QQ_HTTP_handler] 消息监听处理出现异常: ", e);
            // 出现异常，返回信息
            try {
                // 设置响应code和内容长度
                httpExchange.sendResponseHeaders(500, 0);
                OutputStream out = httpExchange.getResponseBody();
                // 响应信息
                IOUtils.write("error", out, this.encoding);
            } catch (IOException e1) {
                QQLog.error("消息响应异常", e1);
            }
        }finally{
            // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
            httpExchange.close();
        }


    }

    /**
     * 通过result获取返回值体
     * @param result 执行结果
     */
    private static String getResultBody(ListenResult result){
        if(result == null){
            return DEFAULT_RESULT;
        }
        // 如果需要截断接下来的插件
        Object resultData = result.result();
        if(result.isToBreakPlugin()){
            // 没有结果
            if(resultData == null){
                return "{ \"block\": true }";
            }
            Object jsonData = JSON.toJSON(resultData);
            if(jsonData instanceof JSONObject){
                ((JSONObject) jsonData).put("block", true);
                return ((JSONObject) jsonData).toJSONString();
            }else if (jsonData instanceof Map){
                try {
                    ((Map) jsonData).put("block", true);
                }catch (Exception e){
                    QQLog.error("响应值转化异常", e);
                }
                return JSON.toJSONString(jsonData);
            }else{
                // 不是object或者map，作为data参数置入。
                Map<String, Object> dataMap = new HashMap<>(2);
                dataMap.put("block", true);
                dataMap.put("data", resultData);
                return JSON.toJSONString(dataMap);
            }
        }else{
            // 不需要截断，将result转化并返回
            if(resultData == null){
                return DEFAULT_RESULT;
            }else{
                return JSON.toJSONString(resultData);
            }
        }
    }


    private static Class<? extends MsgGet> getTypeByPostType(Map<PostType, Map<String, Class<? extends MsgGet>>> typeMap, JSONObject jsonObject){
        // 获取PostType类型
        String postTypeValue = jsonObject.getString(POST_TYPE_KEY_NAME);

        PostType postType;
        try {
            postType = PostType.valueOf(postTypeValue);
        }catch (IllegalArgumentException e){
            return null;
        }

        // 获取对应的Map值
        Map<String, Class<? extends MsgGet>> stringClassMap = typeMap.get(postType);
        if(stringClassMap == null){
            return null;
        }
        // 获取键值
        String keyName = postType.keyName;
        // 获取此key的值
        String string = jsonObject.getString(keyName);
        return stringClassMap.get(string);
    }


}
