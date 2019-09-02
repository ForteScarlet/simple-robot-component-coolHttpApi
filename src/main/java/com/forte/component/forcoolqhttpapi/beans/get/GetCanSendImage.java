package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.CanSendImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 检查是否可以发送图片
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Data
@AllArgsConstructor
public class GetCanSendImage implements Get<CanSendImage> {

    private static final String API = "/can_send_image";
    @Override
    public String getApi(){
        return API;
    }

    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<CanSendImage> getResultType() {
        return CanSendImage.class;
    }
}
