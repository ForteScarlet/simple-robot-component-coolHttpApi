package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.result.AuthInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * 权限信息
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class CoolQAuthInfo implements AuthInfo {

    private String code;
    private String cookies;
    private String csrfToken;
    private String originalData;


}
