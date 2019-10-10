package com.forte.component.forcoolqhttpapi.server;

import com.forte.component.forcoolqhttpapi.beans.msg.Anonymous;
import com.forte.component.forcoolqhttpapi.beans.get.*;
import com.forte.component.forcoolqhttpapi.beans.send.*;
import com.forte.component.forcoolqhttpapi.beans.set.*;
import com.forte.qqrobot.anno.Key;

/**
 *
 * 将部分信息转化为JSON字符串或者JSON封装类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface SendJsonCreator {

    /**
     * 发送私信消息
     * @see #sendPrivateMsg(long, String, boolean)
     */
    SendPrivateMsg sendPrivateMsg(@Key("user_id") long qqCode,
                                  @Key("message") String msg);

    /**
     * 发送私信消息
     * @see #sendPrivateMsg(long, String, boolean)
     */
    SendPrivateMsg sendPrivateMsg(@Key("user_id") String qqCode,
                                  @Key("message") String msg);

    /**
     * 发送私信消息
     * @param qqCode    qqCode号
     * @param msg   信息内容
     * @param autoEscape 自动转码
     */
    SendPrivateMsg sendPrivateMsg(@Key("user_id") long qqCode,
                                  @Key("message") String msg,
                                  @Key("auto_escape") boolean autoEscape);

    /**
     * 发送私信消息
     * @param qqCode    qqCode号
     * @param msg   信息内容
     * @param autoEscape 自动转码
     */
    SendPrivateMsg sendPrivateMsg(@Key("user_id") String qqCode,
                                  @Key("message") String msg,
                                  @Key("auto_escape") boolean autoEscape);

    /**
     * 发送群消息
     * @see #sendGroupMsg(long, String, boolean)
     */
    SendGroupMsg sendGroupMsg(@Key("group_id") long group,
                              @Key("user_id") String msg);

    /**
     * 发送群消息
     * @see #sendGroupMsg(long, String, boolean)
     */
    SendGroupMsg sendGroupMsg(@Key("group_id") String group,
                              @Key("user_id") String msg);

    /**
     * 发送群消息
     * @param group qqCode号
     * @param msg   信息内容
     * @param autoEscape    自动转码
     */
    SendGroupMsg sendGroupMsg(@Key("group_id") long group,
                              @Key("user_id") String msg,
                              @Key("auto_escape") boolean autoEscape);

    /**
     * 发送群消息
     * @param group qqCode号
     * @param msg   信息内容
     * @param autoEscape    自动转码
     */
    SendGroupMsg sendGroupMsg(@Key("group_id") String group,
                              @Key("user_id") String msg,
                              @Key("auto_escape") boolean autoEscape);

    /**
     * 发送讨论组消息
     * @see #sendDiscussMsg(long, String, boolean)
     */
    SendDiscussMsg sendDiscussMsg(@Key("group_id") long group,
                                  @Key("user_id") String msg);

    /**
     * 发送讨论组消息
     * @see #sendDiscussMsg(long, String, boolean)
     */
    SendDiscussMsg sendDiscussMsg(@Key("group_id") String group,
                                  @Key("user_id") String msg);

    /**
     * 发送讨论组消息
     * @param group 讨论组号
     * @param msg   消息
     * @param autoEscape    自动转码
     */
    SendDiscussMsg sendDiscussMsg(@Key("discuss_id") long group,
                                  @Key("message") String msg,
                                  @Key("auto_escape") boolean autoEscape);


    /**
     * 发送讨论组消息
     * @param group 讨论组号
     * @param msg   消息
     * @param autoEscape    自动转码
     */
    SendDiscussMsg sendDiscussMsg(@Key("discuss_id") String group,
                                  @Key("message") String msg,
                                  @Key("auto_escape") boolean autoEscape);

    /**
     * 消息撤回
     * @param id    消息的id
     */
    SendDeleteMsg sendDeleteMsg(@Key("message_id") String id);

    /**
     * 发送赞
     * @param qqCode        qqCode号
     * @param times     赞的次数，每人每天最多10次
     */
    SendLike sendLike(@Key("user_id") long qqCode,
                      @Key("times") int times);

    /**
     * 发送赞
     * @param qqCode        qqCode号
     * @param times     赞的次数，每人每天最多10次
     */
    SendLike sendLike(@Key("user_id") String qqCode,
                      @Key("times") int times);

    /**
     * 发送赞
     * @see #sendLike(long, int)
     */
    SendLike sendLike(@Key("user_id") long qqCode);

    /**
     * 发送赞
     * @see #sendLike(long, int)
     */
    SendLike sendLike(@Key("user_id") String qqCode);

    /**
     * 群踢人
     * @param group     群号
     * @param qqCode        qqCode号
     * @param dontBack  不允许再回来
     * @return
     */
    SetGroupKick setGroupKick(@Key("group_id") long group,
                              @Key("user_id") long qqCode,
                              @Key("reject_add_request") boolean dontBack);

    /**
     * 群踢人
     * @param group     群号
     * @param qqCode        qqCode号
     * @param dontBack  不允许再回来
     * @return
     */
    SetGroupKick setGroupKick(@Key("group_id") String group,
                              @Key("user_id") String qqCode,
                              @Key("reject_add_request") boolean dontBack);

    /**
     * 群踢人
     * @see #setGroupKick(long, long, boolean)
     */
    SetGroupKick setGroupKick(@Key("group_id") long group,
                              @Key("user_id") long qqCode);

    /**
     * 群踢人
     * @see #setGroupKick(long, long, boolean)
     */
    SetGroupKick setGroupKick(@Key("group_id") String group,
                              @Key("user_id")  String qqCode);

    /**
     * 设置群单人禁言
     * @param group 群号
     * @param qqCode    qqCode号
     * @param time  时长，单位秒，如果小于0则为0
     * @return
     */
    SetGroupBan setGroupBan(@Key("group_id") long group,
                            @Key("user_id") long qqCode,
                            @Key("duration") long time);

    /**
     * 设置群单人禁言
     * @param group 群号
     * @param qqCode    qqCode号
     * @param time  时长，单位秒，如果小于0则为0
     * @return
     */
    SetGroupBan setGroupBan(@Key("group_id") String group,
                            @Key("user_id")  String qqCode,
                            @Key("duration") long time);

    /**
     * @see #setGroupBan(long, long, long)
     */
    SetGroupBan setGroupBan(@Key("group_id") long group,
                            @Key("user_id") long qqCode);

    /**
     * @see #setGroupBan(long, long, long)
     */
    SetGroupBan setGroupBan(@Key("group_id") String group,
                            @Key("user_id")  String qqCode);


    /**
     * 群匿名禁言
     * @param group 群号
     * @param flag  flag
     * @param duration  禁言时长 秒
     */
    SetGroupAnonymousBan setGroupAnonymousBan(@Key("group_id") long group,
                                              @Key("anonymous") Anonymous flag,
                                              @Key("duration") long duration);


    /**
     * 群匿名禁言
     * @param group 群号
     * @param flag  flag
     * @param duration  禁言时长 秒
     */
    SetGroupAnonymousBan setGroupAnonymousBan(@Key("group_id") String group,
                                              @Key("anonymous") Anonymous flag,
                                              @Key("duration") long duration);

    /**
     * 群匿名禁言
     * @param group 群号
     * @param flag  flag
     * @param duration  禁言时长 秒
     */
    SetGroupAnonymousBan setGroupAnonymousBan(@Key("group_id") long group,
                                              @Key("anonymous_flag") String flag,
                                              @Key("duration") long duration);

    /**
     * 群匿名禁言
     * @param group 群号
     * @param flag  flag
     * @param duration  禁言时长 秒
     */
    SetGroupAnonymousBan setGroupAnonymousBan(@Key("group_id") String group,
                                              @Key("anonymous_flag") String flag,
                                              @Key("duration") long duration);

    /**
     * 全群禁言
     * @param group     群号
     * @param enable    是否禁言
     */
    SetGroupWholeBan setGroupWholeBan(@Key("group_id")  long group,
                                      @Key("enable")    boolean enable);


    /**
     * 全群禁言
     * @param group     群号
     * @param enable    是否禁言
     */
    SetGroupWholeBan setGroupWholeBan(@Key("group_id")  String group,
                                      @Key("enable")    boolean enable);


    /**
     * 全群禁言
     * @param group     群号
     */
    SetGroupWholeBan setGroupWholeBan(@Key("group_id")  long group);


    /**
     * 全群禁言
     * @param group     群号
     */
    SetGroupWholeBan setGroupWholeBan(@Key("group_id")  String group);



    /**
     * 设置群管理员
     * @param group     群号
     * @param qqCode        qqCode号
     * @param enable    是否设置为
     */
    SetGroupAdmin setGroupAdmin(@Key("group_id") long group,
                                @Key("user_id") long qqCode,
                                @Key("enable") boolean enable);



    /**
     * 设置群管理员
     * @param group     群号
     * @param qqCode        qqCode号
     * @param enable    是否设置为
     */
    SetGroupAdmin setGroupAdmin(@Key("group_id") String group,
                                @Key("user_id")  String qqCode,
                                @Key("enable") boolean enable);

    /**
     * 设置群管理员
     * @param group     群号
     * @param qqCode        qqCode号
     */
    SetGroupAdmin setGroupAdmin(@Key("group_id") long group,
                                @Key("user_id") long qqCode);


    /**
     * 设置群管理员
     * @param group     群号
     * @param qqCode        qqCode号
     */
    SetGroupAdmin setGroupAdmin(@Key("group_id") String group,
                                @Key("user_id")  String qqCode);

    /**
     * 设置群匿名
     * @param group     群号
     * @param enable    开启匿名
     */
    SetGroupAnonymous setGroupAnonymous(@Key("group_id") long group,
                                        @Key("enable") boolean enable);


    /**
     * 设置群匿名
     * @param group     群号
     * @param enable    开启匿名
     */
    SetGroupAnonymous setGroupAnonymous(@Key("group_id") String group,
                                        @Key("enable") boolean enable);

    /**
     * 设置群匿名
     * @param group     群号
     */
    SetGroupAnonymous setGroupAnonymous(@Key("group_id") long group);

    /**
     * 设置群匿名
     * @param group     群号
     */
    SetGroupAnonymous setGroupAnonymous(@Key("group_id") String group);

    /**
     * 设置群名片
     * @param group     群号
     * @param qqCode    qq号
     * @param card      名片，为null或者为空则代表删除名片
     */
    SetGroupCard setGroupCard(@Key("group_id") long group,
                              @Key("user_id") long qqCode,
                              @Key("card") String card);

    /**
     * 设置群名片
     * @param group     群号
     * @param qqCode    qq号
     * @param card      名片，为null或者为空则代表删除名片
     */
    SetGroupCard setGroupCard(@Key("group_id") String group,
                              @Key("user_id")  String qqCode,
                              @Key("card") String card);


    /**
     * 退出群
     * @param group     群号
     * @param isDismiss 是否解散群
     */
    SetGroupLeave setGroupLeave(@Key("group_id") long group,
                                @Key("is_dismiss") boolean isDismiss);


    /**
     * 退出群
     * @param group     群号
     * @param isDismiss 是否解散群
     */
    SetGroupLeave setGroupLeave(@Key("group_id") String group,
                                @Key("is_dismiss") boolean isDismiss);

    /**
     * 退出群
     * @param group     群号
     */
    SetGroupLeave setGroupLeave(@Key("group_id") long group);

    /**
     * 退出群
     * @param group     群号
     */
    SetGroupLeave setGroupLeave(@Key("group_id") String group);

    /**
     * 设置群专属头衔
     * @param group     群号
     * @param qqCode    qq号
     * @param title     头衔
     * @param time      时长
     */
    SetGroupSpecialTitle setGroupSpecialTitle(@Key("group_id") long group,
                                              @Key("user_id") long qqCode,
                                              @Key("special_title") String title,
                                              @Key("duration") long time);

    /**
     * 设置群专属头衔
     * @param group     群号
     * @param qqCode    qq号
     * @param title     头衔
     * @param time      时长
     */
    SetGroupSpecialTitle setGroupSpecialTitle(@Key("group_id") String group,
                                              @Key("user_id")  String qqCode,
                                              @Key("special_title") String title,
                                              @Key("duration") long time);

    /**
     * 设置群专属头衔
     * @param group     群号
     * @param qqCode    qq号
     * @param title     头衔
     */
    SetGroupSpecialTitle setGroupSpecialTitle(@Key("group_id") long group,
                                              @Key("user_id") long qqCode,
                                              @Key("special_title") String title);

    /**
     * 设置群专属头衔
     * @param group     群号
     * @param qqCode    qq号
     * @param title     头衔
     */
    SetGroupSpecialTitle setGroupSpecialTitle(@Key("group_id") String group,
                                              @Key("user_id")  String qqCode,
                                              @Key("special_title") String title);


    /**
     * 退出讨论组
     * @param group 讨论组ID（正常情况下看不到，需要从讨论组消息上报的数据中获得）
     */
    SetDiscussLeave setDiscussLeave(@Key("discuss_id") long group);


    /**
     * 退出讨论组
     * @param group 讨论组ID（正常情况下看不到，需要从讨论组消息上报的数据中获得）
     */
    SetDiscussLeave setDiscussLeave(@Key("discuss_id") String group);

    /**
     * 处理好友申请
     * @param flag      flag
     * @param approve   是否同意
     * @param remark    同意后的备注
     */
    SetFriendAddRequest setFriendAddRequest(@Key("flag") String flag,
                                            @Key("approve") boolean approve,
                                            @Key("remark") String remark);

    /**
     * 处理好友申请
     * @param flag      flag
     * @param remark    同意后的备注
     */
    SetFriendAddRequest setFriendAddRequest(@Key("flag") String flag,
                                            @Key("remark") String remark);

    /**
     *  处理加群请求／邀请
     * @param flag      标识
     * @param type      类别
     * @param approve   是否同意
     * @param reason    备注
     */
    SetGroupAddRequest setGroupAddRequest(@Key("flag") String flag,
                                          @Key("sub_type") String type,
                                          @Key("approve") boolean approve,
                                          @Key("reason") String reason);

    /**
     *  同意加群请求／邀请
     * @param flag      标识
     * @param type      类别
     * @param reason    备注
     */
    SetGroupAddRequest setGroupAddRequest(@Key("flag") String flag,
                                          @Key("sub_type") String type,
                                          @Key("reason") String reason);


    /**
     * 重启插件
     * @param delay 延迟时间
     */
    SetRestartPlugin setRestartPlugin(@Key("delay") long delay);


    /**
     * 获取登录QQ信息
     */
    GetLoginInfo getLoginInfo();


    /**
     * 获取陌生人信息
     * @param qqCode  qq号
     * @param noCache 是否不使用缓存 true：不使用
     */
    GetStrangerInfo getStrangerInfo(@Key("user_id") long qqCode,
                                    @Key("no_cache") boolean noCache);


    /**
     * 获取陌生人信息
     * @param qqCode  qq号
     * @param noCache 是否不使用缓存 true：不使用
     */
    GetStrangerInfo getStrangerInfo(@Key("user_id") String qqCode,
                                    @Key("no_cache") boolean noCache);


    /**
     * 获取陌生人信息
     * @param qqCode  qq号
     */
    GetStrangerInfo getStrangerInfo(@Key("user_id") long qqCode);


    /**
     * 获取陌生人信息
     * @param qqCode  qq号
     */
    GetStrangerInfo getStrangerInfo(@Key("user_id") String qqCode);


    /**
     * 获取群列表
     */
    GetGroupList getGroupList();


    /**
     * 获取群成员详细信息
     * @param qqCode    QQ号
     * @param groupCode 群号
     * @param noCache   是否不使用缓存（使用缓存可能更新不及时，但响应更快）
     */
    GetGroupMemberInfo getGroupMemberInfo(@Key("user_id") long qqCode,
                                          @Key("group_id") long groupCode,
                                          @Key("no_cache") boolean noCache);


    /**
     * 获取群成员详细信息
     * @param qqCode    QQ号
     * @param groupCode 群号
     * @param noCache   是否不使用缓存（使用缓存可能更新不及时，但响应更快）
     */
    GetGroupMemberInfo getGroupMemberInfo(@Key("user_id")    String qqCode,
                                          @Key("group_id") String groupCode,
                                          @Key("no_cache") boolean noCache);


    /**
     * 获取群成员详细信息， 默认使用缓存
     * @param qqCode    QQ号
     * @param groupCode 群号
     */
    GetGroupMemberInfo getGroupMemberInfo(@Key("user_id") long qqCode,
                                          @Key("group_id") long groupCode);


    /**
     * 获取群成员详细信息， 默认使用缓存
     * @param qqCode    QQ号
     * @param groupCode 群号
     */
    GetGroupMemberInfo getGroupMemberInfo(@Key("user_id") String qqCode,
                                          @Key("group_id") long groupCode);


    /**
     * 获取群成员列表
     * @param group 群号
     */
    GetGroupMemberList getGroupMemberList(@Key("group_id") long group);


    /**
     * 获取群成员列表
     * @param group 群号
     */
    GetGroupMemberList getGroupMemberList(@Key("group_id") String group);


    /**
     * 获取Cookies信息
     * 真实的返回值应该是字符串
     */
    GetCookies getCookies();


    /**
     * 获取csrfToken
     * 真实的返回值为字符串
     */
    GetCsrfToken getCsrfToken();


    /**
     * 获取QQ相关凭证
     */
    GetCredentials getCredentials();


    /**
     * 获取语音文件
     */
    GetRecord getRecord();


    /**
     * 获取图片
     */
    GetImage getImage();

    /**
     * 判断是否可以发送图片
     */
    GetCanSendImage getCanSendImage();


    /**
     *  检查是否可以发送语音
     */
    GetCanSendRecord getCanSendRecord();


    /**
     * 获取插件运行状态
     */
    getStatus getStatus();


    /**
     * 获取 酷Q 及 HTTP API 插件的版本信息
     */
    GetVersionInfo getVersionInfo();







}
