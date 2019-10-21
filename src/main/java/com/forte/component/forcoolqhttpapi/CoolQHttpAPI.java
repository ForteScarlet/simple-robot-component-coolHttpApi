package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;

/**
 *
 * 保存特殊API
 * 将不会对公提供构造和setter方法
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpAPI {

    /** 真正的CoolQMsgSender */
    private CoolQHttpMsgSender coolQHttpMsgSender;

    /**
     * 构造不提供公共接口，仅包内结构
     */
    CoolQHttpAPI(CoolQHttpMsgSender sender){
        this.coolQHttpMsgSender = sender;
    }
    CoolQHttpAPI(){ }


    void setCoolQHttpMsgSender(CoolQHttpMsgSender sender){
        this.coolQHttpMsgSender = sender;
    }

    /**
     * 获取真正的MsgSender对象
     */
    public CoolQHttpMsgSender getMsgSender(){
        return coolQHttpMsgSender;
    }




}
