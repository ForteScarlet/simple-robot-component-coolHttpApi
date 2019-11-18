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
    private static final Map<String, String> ERROR_MESSAGE = new HashMap<String, String>(16){{
//        put(0  , "操作成功");
//        put(1  , "操作已进入异步执行，具体结果未知");
        put("100", "参数缺失或参数无效，通常是因为没有传入必要参数, 某些接口中也可能因为参数明显无效（比如传入的 QQ 号小于等于 0，此时无需调用 酷Q 函数即可确定失败）");
        put("102", "酷Q 函数返回的数据无效，一般是因为传入参数有效但没有权限，比如试图获取没有加入的群组的成员列表");
        put("103", "操作失败，一般是因为用户权限不足，或文件系统异常、不符合预期");
        put("104", "由于 酷Q 提供的凭证（Cookie 和 CSRF Token）失效导致请求 QQ 相关接口失败，可尝试清除 酷Q 缓存来解决");
        put("201", "工作线程池未正确初始化（无法执行异步任务）");
    }};


    /**
     * 根据返回码判断是否失败
     * @param baseData
     */
    public static void requireNotFailed(JSONObject baseData){
        // 需要是原生的数据，有status字段的那种
        String status = baseData.getString("status");
        String message = ERROR_MESSAGE.get(status);
        if(message != null){
           throw new CoolQHttpInteractionException(errorMsg(status, message));
        }
    }


    public static String errorMsg(String code){
        return errorMsg(code, ERROR_MESSAGE.get(code));
    }

    public static String errorMsg(String code, String msg){
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

    public CoolQHttpInteractionException(String message) {
        super(message);
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
}
