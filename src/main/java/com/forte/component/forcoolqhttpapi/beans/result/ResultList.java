package com.forte.component.forcoolqhttpapi.beans.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 * 返回值为列表的时候，通过实例化一个列表对象并将数组对象封装进去
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface ResultList<R extends ResultInner> extends Result {

    /** 获取数组类型 */
    @JSONField(serialize = false)
    Class<R> getListType();

}
