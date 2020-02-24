package com.forte.component.forcoolqhttpapi.server;

import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.utils.JSONDataUtil;
import com.forte.lang.Language;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.log.QQLogLang;
import com.forte.qqrobot.sender.senderlist.SenderList;
import com.forte.qqrobot.utils.ObjectsPlus;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.IOUtils;

import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * http监听服务所监听的服务工厂类
 * <p>
 * 一般需要监听的消息就是那些监听消息类型
 * 可以尝试直接通过包扫描进行创建
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpHandlerFactory {

    /** factory中使用的log日志 */
    protected static final QQLogLang LOG_LANG = new QQLogLang("cq.httpapi.handler.factory");
    protected QQLogLang getLog(){
        return LOG_LANG;
    }

    /**
     * post_type上报类型的key值
     */
    public static final String POST_TYPE_KEY = "post_type";
    // message、notice、request
    /*
        以下为post_type参数可能的值
     */

    public static final String POST_TYPE_MESSAGE = "message";
    public static final String POST_TYPE_NOTICE = "notice";
    public static final String POST_TYPE_REQUEST = "request";

    /*
        以下为三种上报类型相对应的数据类型的值的key值
     */

    public static final String MSG_TYPE_KEY_MESSAGE = "message_type";
    public static final String MSG_TYPE_KEY_NOTICE = "notice_type";
    public static final String MSG_TYPE_KEY_REQUEST = "request_type";

    /**
     * posttype与对应类型的key的对应关系
     */
    private static final Map<String, String> POST_TPYE_FOR_MSG_TYPE_KEY = new HashMap<String, String>(4) {{
        put(POST_TYPE_MESSAGE, MSG_TYPE_KEY_MESSAGE);
        put(POST_TYPE_NOTICE, MSG_TYPE_KEY_NOTICE);
        put(POST_TYPE_REQUEST, MSG_TYPE_KEY_REQUEST);
    }};

    // 到时候直接扫描这个包就行了

    public static final String MSG_GET_PACK = "com.forte.component.forcoolqhttpapi.beans.msg";

    /**
     * 创建所有的监听消息handler
     *
     * @param encode          编码格式，一般为UTF-8
     * @param methods         监听的请求类型，一般固定为POST
     * @param listenerManager 监听消息分配器
     * @param sender          送信器实例对象
     */
    @Deprecated
    public static Map<String, HttpHandler> getHandlerMap(String encode, String[] methods, ListenerManager listenerManager, CoolQHttpMsgSender sender) {
        // 扫描所有的消息类型

        return null;



    }


    /**
     * HttpHandler实例对象
     * 与另一个httpapi不同的是，coolqHttpAPI一般来讲一个接口仅监听一个类型的消息，所以需要多填两个参数
     */
    public static class CoolQHttpHanlder implements com.sun.net.httpserver.HttpHandler {
        /**
         * 上报参数中对应消息类型的key值, 通过JSONObject对象与参数中传入的监听消息类型进行判断
         */
        private static final BiPredicate<JSONObject, String> MSG_TYPE_OK = (json, mt) -> {
            String postType = json.getString(POST_TYPE_KEY);
            // 获取对应的key值
            String msgTypeKey = Objects.requireNonNull(POST_TPYE_FOR_MSG_TYPE_KEY.get(postType),
                    Language.format("cq.httpapi.handler.factory.unknownType", postType));
            // 获取此key值并判断类型
            return json.getString(msgTypeKey).equalsIgnoreCase(mt);
        };

        private final String ENCODE;
        private final String[] METHODS;
        private final ListenerManager MANAGER;
        private final SenderList SENDER;
        /**
         * 为了优化效率，在构造的时候先提前实现好method判断函数
         */
        private final Predicate<String> IS_METHOD_OK;

        /**
         * message_type参数值或notice_type参数值或request_type参数值
         */
        private final String MESSAGE_TYPE;

        /**
         * 对应封装类类型
         */
        private final Class<? extends MsgGet> MSG_TYPE;

        /**
         * 构造
         *
         * @param encode  编码格式， 一般应该为UTF-8
         * @param methods 请求类型， 一般应该为POST
         * @param manager 消息分发器
         * @param sender  送信器实例
         */
        public CoolQHttpHanlder(String encode, String[] methods, ListenerManager manager, SenderList sender,
                                // 此参数为监听的消息类型的字符串，
                                // 只有当此类型字符串匹配了才会获取, 例如：private 、 group等
                                String messageType,
                                // 此参数为此类型的消息, 用于将Json数据机进行封装
                                Class<? extends MsgGet> msgType
        ) {
            // 全都不能是null，一个都别想跑
            ObjectsPlus.allNonNull(encode, methods, manager, sender, messageType, msgType);

            this.ENCODE = encode;
            this.METHODS = methods;
            this.MANAGER = manager;
            this.SENDER = sender;
            this.MESSAGE_TYPE = messageType;
            this.MSG_TYPE = msgType;
            if (methods.length == 1) {
                // 只有一个参数被允许，则直接拿出来进行判断
                String mType = methods[0];
                this.IS_METHOD_OK = m -> m.equalsIgnoreCase(mType);
            } else if (methods.length > 1) {
                // 使用set保存所有类型，获取的时候则不会每次都遍历了
                Set<String> methodSet = new HashSet<String>(4) {{
                    for (String m : methods) {
                        add(m.toLowerCase());
                    }
                }};
                this.IS_METHOD_OK = m -> methodSet.contains(m.toLowerCase());
            } else {
                throw new RobotRuntimeException("没有任何请求类型被允许！至少应当允许[POST]类型");
            }
        }

        /**
         * 消息接收器
         */
        @Override
        public void handle(HttpExchange httpExchange) {
            //使用线程异步接收消息
            ResourceDispatchCenter.getThreadPool().execute(() -> doHandle(httpExchange));
        }

        /**
         * 接收到消息的逻辑
         */
        private void doHandle(HttpExchange httpExchange) {
            try {
                //获得表单提交数据
                //判断请求方式
                String method = httpExchange.getRequestMethod();
                if (IS_METHOD_OK.test(method)) {
                    // 如果类型被允许，则获取消息
                    //获取接收到的参数
                    InputStream requestBody = httpExchange.getRequestBody();
                    //编码转义
                    String paramsUrl = IOUtils.toString(requestBody, ENCODE);
                    String params = URLDecoder.decode(paramsUrl, ENCODE);

                    // 获取参数，如果message_type参数匹配则进行类转化，否则无视并直接返回接收成功消息。
                    // 一般来讲，通过此接口传来的参数不可能是别的
                    JSONObject paramJson = JSONDataUtil.toJSONObject(params);
                    if (MSG_TYPE_OK.test(paramJson, MESSAGE_TYPE)) {
                        // 如果判断成功，则对消息进行封装并上报
                        MsgGet msgGet = paramJson.toJavaObject(MSG_TYPE);
                        MANAGER.onMsg(msgGet, SENDER);
                    }
                }
                // 返回响应
                // 等日后响应策略改变后再改，现在先固定类型, 默认为空json字符串
                response(httpExchange, 200, 0, "{}");

            } catch (Exception e) {
                // 出现异常
                // 设置响应code和内容长度
                try {
//                    httpExchange.sendResponseHeaders(500, 0);
//                    OutputStream out = httpExchange.getResponseBody();
//                    // 响应信息
//                    IOUtils.write("error", out, ENCODE);

                    response(httpExchange, 500, 0, "error: " + e.getMessage());
                    // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
                } catch (IOException e1) {
                    e = new RobotRuntimeException("异常错误信息响应失败！", e1);

                }
                // 响应完成后重新抛出异常
                throw new RobotRuntimeException("监听消息响应异常！", e);
            } finally {
                // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
                httpExchange.close();
            }
        }


        /**
         * 响应数据， 不会关流所以请记得关闭httpExchange
         * @param httpExchange httpExchange对象
         * @param respHeaderCode 头信息的响应值
         * @param respHeaderL 不知道是啥，反正默认就0吧
         * @param data 响应参数值
         */
        public void response(HttpExchange httpExchange,
                               int respHeaderCode , long respHeaderL,
                               String data
                               ) throws IOException {
            httpExchange.sendResponseHeaders(respHeaderCode, respHeaderL);

            // 配置响应头 - json
            httpExchange.getResponseHeaders().add("Content-Type", "application/json");
            /*
                {
                    "block": true
                }
                则代表不再向下级插件报消息
             */

            IOUtils.write(data, httpExchange.getResponseBody(), ENCODE);
        }

    }

}
