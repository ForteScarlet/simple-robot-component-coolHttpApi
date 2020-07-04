/*
 * Copyright (c) 2020. ForteScarlet All rights reserved.
 * Project  simple-robot-component-coolHttpApi
 * File     QQImage.java
 *
 * You can contact the author through the following channels:
 * github https://github.com/ForteScarlet
 * gitee  https://gitee.com/ForteScarlet
 * email  ForteScarlet@163.com
 * QQ     1149159218
 *
 *
 */

package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.result.ImageInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

/**
 * QQ图片
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class QQImage implements Result, ImageInfo {
    /*
        响应数据
        字段名	数据类型	说明
        file	string	下载后的图片文件路径，如 C:\Apps\CoolQ\data\image\6B4DE3DFD1BD271E3297859D41C530F5.jpg
     */

    private String file;
    private String originalData;


    @Override
    public String getMD5() {
        return null;
    }

    @Override
    public Double getWidth() {
        return -1.0;
    }

    @Override
    public Double getHeight() {
        return -1.0;
    }

    @Override
    public Long getSize() {
        return -1L;
    }

    /**
     * 路径
     */
    @Override
    public String getUrl() {
        return file;
    }

    @Override
    public Long getTime() {
        return -1L;
    }

    @Override
    public String getFileBase64() {
        return null;
    }

    /**
     * 获取图片文件的文姓下载后的路径
     */
    public String getFilePath() {
        return file;
    }

    /**
     * 获取图片文件本体
     */
    public File getFile() {
        return new File(getFilePath());
    }


}
