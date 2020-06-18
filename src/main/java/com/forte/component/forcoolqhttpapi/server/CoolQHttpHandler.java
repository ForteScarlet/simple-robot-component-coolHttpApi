package com.forte.component.forcoolqhttpapi.server;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.beans.msg.PostType;
import com.forte.qqrobot.MsgParser;
import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.log.QQLogLang;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * http数据处理对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpHandler implements HttpHandler {

    /**
     * 此处的日志前缀为cq.httpapi.handler
     */
    protected static final QQLogLang LOG_LANG = new QQLogLang("cq.httpapi.handler");

    protected QQLogLang getLog() {
        return LOG_LANG;
    }

    /**
     * 编码格式
     */
    private String encoding;

    /**
     * 可以接受的请求方式
     */
    private String[] methods;

    /**
     * 用于判断请求类型是否正确的函数
     */
    private Predicate<String> isMethod;

    /**
     * 默认返回值
     */
    private static final String DEFAULT_RESULT = "{}";

    /**
     * post_type 的键的值
     */
    private static final String POST_TYPE_KEY_NAME = "post_type";

    /**
     * 消息处理器
     */
    private final MsgProcessor processor;

    /**
     * 字符串消息解析器
     */
    private final MsgParser parser;

    /**
     * SHA1 验证密钥
     */
    private String secret;

    /*
        如果 secret 配置项也不为空，则会在每次上报的请求头中加入 HMAC 签名，如：
        POST / HTTP/1.1
        X-Signature: sha1=f9ddd4863ace61e64f462d41ca311e3d2c1176e2
        X-Self-ID: 123456
     */


    /**
     * 构造，提供所需参数
     *
     * @param encoding  编码格式，一般来讲就是固定为utf-8了
     * @param methods   请求方式，一般来讲就是post了
     * @param secret    API密钥验证
     * @param processor 监听消息管理器
     * @param parser    监听消息管理器
     */
    public CoolQHttpHandler(String encoding,
                            String[] methods,
                            String secret,
                            MsgProcessor processor,
                            MsgParser parser) {
        this.encoding = encoding;
        this.methods = methods;
        this.secret = secret;

        this.processor = processor;
        this.parser = parser;

        if (methods == null || methods.length == 0) {
            // 如果参数中methods没东西或者为空，默认使用post类型
            this.isMethod = "post"::equalsIgnoreCase;
        } else if (methods.length == 1) {
            String m = methods[0];
            this.isMethod = me -> (me != null) && (me.equalsIgnoreCase(m));
        } else {
            // 有多个
            this.isMethod = me -> {
                if (me == null) {
                    return false;
                }
                for (String method : methods) {
                    if (me.equalsIgnoreCase(method)) {
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
    public void doHandle(HttpExchange httpExchange) {
        try {
            //获得表单提交数据
            //判断请求方式
            String method = httpExchange.getRequestMethod();
            // 如果请求方式正确，进行下一步
            if (isMethod.test(method)) {

                //获取接收到的参数
                InputStream requestBody = httpExchange.getRequestBody();
                //编码转义  貌似不再需要编码转义

                String paramsUrl = IoUtil.read(requestBody, this.encoding);
                // 如果密钥不为null，验证密钥
                if (secret != null) {
                    // 获取请求头中的验证密钥
                    // X-Signature: sha1=xxx
                    String xSignature = httpExchange.getRequestHeaders().getFirst("X-Signature");
                    if (xSignature == null || (xSignature = xSignature.trim()).length() < 4 || !xSignature.startsWith("sha1")) {
                        // 没有密钥或者长度根本不对或者开头不是'sha1'
                        getLog().warning("onmessage.verify.failed", xSignature);
                        // 出现异常，返回信息
                        response(httpExchange, 401, 0, "no secret");
                        // 结束。
                        return;
                    } else {
                        xSignature = xSignature.substring(5);
                    }

                    boolean verify = secretVerification(paramsUrl, secret, xSignature);
                    if (!verify) {
                        getLog().debug("secret.verify.failed", xSignature);
                        response(httpExchange, 403, 0, "secret verify failed");
                        // 结束方法
                        return;
                    }
                    // 验证成功，放行。
                }


                getLog().debug("onmessage", paramsUrl);

                // 转化为MsgGet
                MsgGet msgGet = parser.parse(paramsUrl);

                if (msgGet != null) {
                    ListenResult<?> result = processor.onMsgSelected(msgGet);
                    // 相应消息内容
                    String resultBody = getResultBody(result);
                    // 响应数据
                    // 设置响应code和内容长度
                    response(httpExchange, 200, 0, resultBody);
                }
            } else {
                // 不是支持的请求方式，返回405类型
                response(httpExchange, 405, 0, "no method type");
            }
        } catch (Exception e) {
            getLog().error("onmessage.failed", e);
            // 出现异常，返回信息
            response(httpExchange, 500, 0, "error");
        } finally {
            // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
            httpExchange.close();
        }


    }

    /**
     * 通过result获取返回值体
     *
     * @param result 执行结果
     */
    private String getResultBody(ListenResult result) {
        if (result == null) {
            return DEFAULT_RESULT;
        }
        // 如果需要截断接下来的插件
        Object resultData = result.result();
        if (result.isToBreakPlugin()) {
            // 没有结果
            if (resultData == null) {
                return "{ \"block\": true }";
            }
            Object jsonData = JSON.toJSON(resultData);
            if (jsonData instanceof JSONObject) {
                ((JSONObject) jsonData).put("block", true);
                return ((JSONObject) jsonData).toJSONString();
            } else if (jsonData instanceof Map) {
                try {
                    ((Map) jsonData).put("block", true);
                } catch (Exception e) {
                    getLog().error("response.format.failed", e);
                }
                return JSON.toJSONString(jsonData);
            } else {
                // 不是object或者map，作为data参数置入。
                Map<String, Object> dataMap = new HashMap<>(2);
                dataMap.put("block", true);
                dataMap.put("data", resultData);
                return JSON.toJSONString(dataMap);
            }
        } else {
            // 不需要截断，将result转化并返回
            if (resultData == null) {
                return DEFAULT_RESULT;
            } else {
                return JSON.toJSONString(resultData);
            }
        }
    }

    /**
     * 相应消息
     *
     * @param httpExchange
     * @param statusCode
     * @param l
     * @param writeData
     */
    private void response(HttpExchange httpExchange, int statusCode, int l, String writeData) {
        // 出现异常，返回信息
        try {
            // 设置响应code和内容长度
            httpExchange.sendResponseHeaders(statusCode, l);
            OutputStream out = httpExchange.getResponseBody();
            // 响应信息
            IoUtil.writeUtf8(out, false, writeData);
            getLog().debug("response", writeData);
        } catch (IOException e) {
            getLog().error("response.failed", e);
        }
    }


    private static Class<? extends MsgGet> getTypeByPostType(Map<PostType, Map<String, Class<? extends MsgGet>>> typeMap, JSONObject jsonObject) {
        // 获取PostType类型
        String postTypeValue = jsonObject.getString(POST_TYPE_KEY_NAME);

        PostType postType;
        try {
            postType = PostType.valueOf(postTypeValue);
        } catch (IllegalArgumentException e) {
            return null;
        }

        // 获取对应的Map值
        Map<String, Class<? extends MsgGet>> stringClassMap = typeMap.get(postType);
        if (stringClassMap == null) {
            return null;
        }
        // 获取键值
        String keyName = postType.keyName;
        // 获取此key的值
        String string = jsonObject.getString(keyName);
        return stringClassMap.get(string);
    }


    /**
     * 根据正文于secret验证监听消息
     *
     * @param text     请求正文
     * @param secret   密钥
     * @param contrast 对比的secret
     * @return
     */
    private boolean secretVerification(String text, String secret, String contrast) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String a = genHMAC(text, secret);
        System.out.println("this HMAC: " + a);
        System.out.println("header:    " + contrast);
        return Objects.equals(a, contrast);
    }


    /**
     * HMAC-SHA1算法
     * @param data 正文
     * @param key  密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private String genHMAC(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        String sha1 = "HmacSHA1";
        SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), sha1);
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(sha1);
        //用给定密钥初始化 Mac 对象
        mac.init(signinKey);

        //完成 Mac 操作
        byte[] rawHmac = mac.doFinal(data.getBytes());

        return byteToHex(rawHmac);
    }

    /**
     * byte[]转hex
     */
    public static String byteToHex(byte[] bytes){
        String strHex = "";
        StringBuilder sb = new StringBuilder(bytes.length << 1);
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            // 每个字节由两个字符表示，位数不够，高位补0
            if((strHex.length() == 1)){
                sb.append('0').append(strHex);
            }else{
                sb.append(strHex);
            }
        }
        return sb.toString().trim();
    }

}
