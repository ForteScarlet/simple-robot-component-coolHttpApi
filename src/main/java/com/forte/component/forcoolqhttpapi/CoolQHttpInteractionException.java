package com.forte.component.forcoolqhttpapi;

import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.exception.RobotRuntimeException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * CoolQ HTTP API 通讯异常
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpInteractionException extends RobotRuntimeException {
    /*
    汇总如下：
        retcode	说明
        0	同时 status 为 ok，表示操作成功
        1	同时 status 为 async，表示操作已进入异步执行，具体结果未知
        100	参数缺失或参数无效，通常是因为没有传入必要参数，
        某些接口中也可能因为参数明显无效（比如传入的 QQ 号小于等于 0，此时无需调用 酷Q 函数即可确定失败），
        此项和以下的 status 均为 failed
        102	酷Q 函数返回的数据无效，一般是因为传入参数有效但没有权限，比如试图获取没有加入的群组的成员列表
        103	操作失败，一般是因为用户权限不足，或文件系统异常、不符合预期
        104	由于 酷Q 提供的凭证（Cookie 和 CSRF Token）失效导致请求 QQ 相关接口失败，可尝试清除 酷Q 缓存来解决
        201	工作线程池未正确初始化（无法执行异步任务）

        一共这么几个，暂时干脆写死了吧
        后期再细化
     */
    /**
     * 异常信息保存
     */
    private static final Map<Integer, String> ERROR_MESSAGE = new HashMap<Integer, String>(4){{
//        put(0  , "操作成功");
//        put(1  , "操作已进入异步执行，具体结果未知");
        put(100, "参数缺失或参数无效，通常是因为没有传入必要参数, 某些接口中也可能因为参数明显无效（比如传入的 QQ 号小于等于 0，此时无需调用 酷Q 函数即可确定失败）");
        put(102, "酷Q 函数返回的数据无效，一般是因为传入参数有效但没有权限，比如试图获取没有加入的群组的成员列表");
        put(103, "操作失败，一般是因为用户权限不足，或文件系统异常、不符合预期");
        put(104, "由于 酷Q 提供的凭证（Cookie 和 CSRF Token）失效导致请求 QQ 相关接口失败，可尝试清除 酷Q 缓存来解决");
        put(201, "工作线程池未正确初始化（无法执行异步任务）");
    }};


    /**
     * 根据返回码判断是否失败
     * @param baseData
     */
    public static void requireNotFailed(JSONObject baseData){
        // 需要是原生的数据，有status字段、retcode字段的那种
        String status  = baseData.getString("status");
        if("failed".equals(status)){
            int retcode = baseData.getInteger("retcode");
            String message = ERROR_MESSAGE.get(retcode);
            if(message == null){
                message = "非CQ HTTP API错误码, 请启用酷Q开发者模式并查看详细错误信息。介绍：https://docs.cqp.im/dev/v9/errorcode/";
            }
            throw new CoolQHttpInteractionException(errorMsg(retcode, message));
        }
    }


    public static String errorMsg(int code){
        return errorMsg(code, ERROR_MESSAGE.get(code));
    }

    public static String errorMsg(int code, String msg){
        StringBuilder sb = new StringBuilder("与插件的数据交互出现异常！");
        sb.append("\r\nerror code:\t").append(code);
        if(msg != null){
            sb.append("\r\nerror info:\t").append(msg);
        }

        sb.append("\r\n");
        return sb.toString();
    }



    public CoolQHttpInteractionException() {
    }

    public CoolQHttpInteractionException(String message, Object... format) {
        super(message, format);
    }

    public CoolQHttpInteractionException(String message) {
        super(message);
    }

    public CoolQHttpInteractionException(String message, Throwable cause, Object... format) {
        super(message, cause, format);
    }

    public CoolQHttpInteractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoolQHttpInteractionException(Throwable cause) {
        super(cause);
    }

    public CoolQHttpInteractionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CoolQHttpInteractionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
        super(message, cause, enableSuppression, writableStackTrace, format);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     */
    public CoolQHttpInteractionException(int pointless, String message) {
        super(pointless, message);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless 无意义参数，填任意值 pointless param
     * @param message   信息正文
     * @param cause     异常
     */
    public CoolQHttpInteractionException(int pointless, String message, Throwable cause) {
        super(pointless, message, cause);
    }

    /**
     * 不进行语言国际化转化的构造方法
     *
     * @param pointless          无意义参数，填任意值 pointless param
     * @param message            信息正文
     * @param cause              异常
     * @param enableSuppression  whether or not suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether or not the stack trace should
     */
    public CoolQHttpInteractionException(int pointless, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(pointless, message, cause, enableSuppression, writableStackTrace);
    }
}
