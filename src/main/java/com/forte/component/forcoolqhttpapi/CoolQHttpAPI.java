/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     CoolQHttpAPI.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi;

import com.forte.component.forcoolqhttpapi.server.CoolQHttpMsgSender;

/**
 * 弃用
 **/
@Deprecated
public class CoolQHttpAPI {

    /**
     * 真正的CoolQMsgSender
     */
    private CoolQHttpMsgSender coolQHttpMsgSender;

    /**
     * 构造不提供公共接口，仅包内结构
     */
    CoolQHttpAPI(CoolQHttpMsgSender sender) {
        this.coolQHttpMsgSender = sender;
    }

    CoolQHttpAPI() {
    }


    void setCoolQHttpMsgSender(CoolQHttpMsgSender sender) {
        this.coolQHttpMsgSender = sender;
    }

    /**
     * 获取真正的MsgSender对象
     */
    public CoolQHttpMsgSender getMsgSender() {
        return coolQHttpMsgSender;
    }


}
