package com.forte.component.forcoolqhttpapi;

import com.forte.qqrobot.BaseConfiguration;
import com.forte.qqrobot.ResourceApplication;

import java.io.InputStream;
import java.util.Objects;

/**
 *
 * 配置读取启动器接口
 * 1.8.x之后，如果不是需要自定义输入流，推荐使用注解配置
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface CoolQHttpResourceApp extends ResourceApplication<CoolQHttpConfiguration> {


    /**
     * resource路径下的配置文件的文件名称。
     * 需要是个properties文件。<br>
     * 例如：<code>return "/httpapi-config.properties";</code> <br>
     * 假如文件不存在于resource路径下，需要自行获取InputStream流，则请重写{@link #getStream()} 方法而无视此方法。
     * <br>
     * 否则请不要重写{@link #getStream()} 方法和 {@link #before(BaseConfiguration)} 方法。
     * @return resource下配置文件路径
     */
    String resourceName();

    /**
     * 通过读取resource下的配置文件来获取配置文件的输入流。<br>
     * 如果需要自定义输入流的获取，请重写此方法而无视{@link #resourceName()} <br>
     * 否则请不要重写此方法。
     * @return 配置文件输入流
     */
    @Override
    default InputStream getStream(){
        InputStream stream = this.getClass().getResourceAsStream(resourceName());
        return Objects.requireNonNull(stream, "未读取到配置文件 : resource inputstream is null.");
    }


}
