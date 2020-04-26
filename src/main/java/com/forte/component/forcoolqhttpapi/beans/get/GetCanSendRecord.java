package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.CanSendRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 检查是否可以发送语音
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class GetCanSendRecord implements Get<CanSendRecord> {

    public static final String API = "/can_send_record";

    @Override
    public String getApi() {
        return API;
    }


    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<CanSendRecord> getResultType() {
        return CanSendRecord.class;
    }
}
