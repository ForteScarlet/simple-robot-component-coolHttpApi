package com.forte.component.forcoolqhttpapi;

/**
 * 无服务启动现在依赖于核心中的 {@code core.enableServer} 配置来控制
 **/
@Deprecated
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
