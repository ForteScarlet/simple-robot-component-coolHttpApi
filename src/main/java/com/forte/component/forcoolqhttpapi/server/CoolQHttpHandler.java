package com.forte.component.forcoolqhttpapi.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.beans.msg.PostType;
import com.forte.component.forcoolqhttpapi.utils.JSONDataUtil;
import com.forte.qqrobot.MsgParser;
import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.types.ResultSelectType;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.log.QQLogLang;
import com.forte.qqrobot.sender.senderlist.RootSenderList;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * http数据处理对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpHandler implements HttpHandler {

    /** 此处的日志前缀为cq.httpapi.handler */
    protected static final QQLogLang LOG_LANG = new QQLogLang("cq.httpapi.handler");
    protected QQLogLang getLog(){
        return LOG_LANG;
    }

    /** 编码格式 */
    private String encoding;

    /** 可以接受的请求方式 */
    private String[] methods;

    /** 监听消息管理器 */
    private ListenerManager manager;

    /**
     * 用于判断请求类型是否正确的函数
     */
    private Predicate<String> isMethod;

    /** 默认返回值 */
    private static final String DEFAULT_RESULT = "{}";

    /** post_type 的键的值 */
    private static final String POST_TYPE_KEY_NAME = "post_type";

    /** 消息处理器 */
    private final MsgProcessor processor;

    /** 字符串消息解析器 */
    private final MsgParser parser;

    /**
     * 构造，提供所需参数
     * @param encoding  编码格式，一般来讲就是固定为utf-8了
     * @param methods   请求方式，一般来讲就是post了
     * @param manager   监听消息管理器
     */
    public CoolQHttpHandler(String encoding,
                            String[] methods,
                            ListenerManager manager,
                            MsgProcessor processor,
                            MsgParser parser){
        this.encoding = encoding;
        this.methods = methods;
        this.manager = manager;

        this.processor = processor;
        this.parser = parser;

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

                getLog().debug("onmessage", paramsUrl);

                // 转化为MsgGet
                MsgGet msgGet = parser.parse(paramsUrl);

                if(msgGet != null){
                    ListenResult<?> result = processor.onMsgSelected(msgGet);
                    // 响应数据
                    // 设置响应code和内容长度
                    httpExchange.sendResponseHeaders(200, 0);

                    //获取响应输出流
                    OutputStream out = httpExchange.getResponseBody();

                    // 相应消息内容
                    String resultBody = getResultBody(result);

                    // 响应信息
                    IOUtils.write(resultBody, out, this.encoding);
                    getLog().debug("response", resultBody);
                }
            }else{
                // 不是支持的请求方式，返回405类型
                try {
                    // 设置响应code和内容长度
                    httpExchange.sendResponseHeaders(405, 0);
                    OutputStream out = httpExchange.getResponseBody();
                    // 响应信息
                    IOUtils.write("no method type", out, this.encoding);
                    getLog().debug("response", "no method type");

                } catch (IOException e1) {
                    getLog().error("response.failed", e1);
                }
            }
        }catch (Exception e){
            getLog().error("onmessage.failed", e);
            // 出现异常，返回信息
            try {
                // 设置响应code和内容长度
                httpExchange.sendResponseHeaders(500, 0);
                OutputStream out = httpExchange.getResponseBody();
                // 响应信息
                IOUtils.write("error", out, this.encoding);
                getLog().debug("response", "error");
            } catch (IOException e1) {
                getLog().error("response.failed", e1);
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
    private String getResultBody(ListenResult result){
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
                    getLog().error("response.format.failed", e);
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
