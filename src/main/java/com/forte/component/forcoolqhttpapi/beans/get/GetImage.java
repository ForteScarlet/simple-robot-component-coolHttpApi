/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     GetImage.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.beans.get;

import com.forte.component.forcoolqhttpapi.beans.result.QQImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 获取图片
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetImage implements Get<QQImage> {
    /*
        参数
        字段名	数据类型	默认值	说明
        file	string	-	收到的图片文件名（CQ 码的 file 参数），如 6B4DE3DFD1BD271E3297859D41C530F5.jpg
     */

    private String file;
    public static final String API = "/get_image";

    @Override
    public String getApi() {
        return API;
    }


    /**
     * 获取响应值的接收封装类型，需要是一个具体的实现类
     */
    @Override
    public Class<QQImage> getResultType() {
        return QQImage.class;
    }
}
