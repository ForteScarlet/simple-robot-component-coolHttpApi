package com.forte.component.forcoolqhttpapi;

import com.forte.qqrobot.BaseConfiguration;

/**
 * 无服务配置类, 将与服务相关的配置标记为过时
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQNoServerConfiguration extends CoolQHttpConfiguration {

    @Override
    @Deprecated
    public int getBackLog() {
        return super.getBackLog();
    }

    @Override
    @Deprecated
    public CoolQNoServerConfiguration setBackLog(int backLog) {
        return this;
    }

    @Override
    @Deprecated
    public int getJavaPort() {
        return super.getJavaPort();
    }

    @Override
    @Deprecated
    public CoolQNoServerConfiguration setJavaPort(int javaPort) {
        return this;
    }

    @Override
    @Deprecated
    public String[] getMethod() {
        return super.getMethod();
    }

    @Override
    @Deprecated
    public CoolQNoServerConfiguration setMethod(String[] method) {
        return this;
    }
}
