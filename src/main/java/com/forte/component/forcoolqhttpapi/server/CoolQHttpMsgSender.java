package com.forte.component.forcoolqhttpapi.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.CoolQHttpInteractionException;
import com.forte.component.forcoolqhttpapi.beans.get.*;
import com.forte.component.forcoolqhttpapi.beans.msg.Anonymous;
import com.forte.component.forcoolqhttpapi.beans.result.*;
import com.forte.component.forcoolqhttpapi.beans.send.*;
import com.forte.component.forcoolqhttpapi.beans.set.*;
import com.forte.qqrobot.beans.messages.QQCodeAble;
import com.forte.qqrobot.beans.messages.result.GroupList;
import com.forte.qqrobot.beans.messages.result.GroupMemberList;
import com.forte.qqrobot.beans.messages.result.StrangerInfo;
import com.forte.qqrobot.beans.messages.result.*;
import com.forte.qqrobot.beans.messages.result.inner.GroupNote;
import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.exception.RobotRuntimeException;
import com.forte.qqrobot.sender.HttpClientAble;
import com.forte.qqrobot.sender.HttpClientHelper;
import com.forte.qqrobot.sender.senderlist.BaseRootSenderList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 真正的送信器并实现接口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class CoolQHttpMsgSender extends BaseRootSenderList {

    protected static HttpClientAble getHttp() {
        return HttpClientHelper.getDefaultHttp();
    }

    /**
     * 空的jsonObject，请不要对其内容进行任何修改
     */
    private static final JSON EMPTY_JSON = new JSONObject();

    /**
     * 当前的送信器对应的botInfo
     */
    private BotInfo botInfo;

    /**
     * 额外的请求头
     */
    private Map<String, String> header = new HashMap<>(1);


    protected BotInfo getBotInfo() {
        return botInfo;
    }

    /**
     * 构造
     * 内部需要一个BotManager对象与一个ThisCodeAble对象
     */
    public CoolQHttpMsgSender(BotInfo botInfo) {
        this.botInfo = botInfo;
    }

    /**
     * 构造
     * 内部需要一个BotManager对象与一个ThisCodeAble对象
     */
    public CoolQHttpMsgSender(BotInfo botInfo, String token) {
        this.botInfo = botInfo;
        /*
         * accessToken
         * 如果存在，则会在请求的时候从请求头中增加：
         * Authorization: Bearer ${token}
         */
        if(token != null) {
            // Authorization: Bearer ${token}
            header.put("Authorization", "Bearer " + token);
        }
    }


    /**
     * 请求接口，并返回原生值字符串
     *
     * @return 返回值原生字符串
     * @throws Exception 可能会出现请求返回值错误
     */
    private String getResultJson(String requestPath, String requestJson) {
        //获取HTTP API请求地址参数
        String url = getBotInfo().getPath() + requestPath;
        //请求并返回响应数据
        return getHttp().postJson(url, requestJson, null, header);
    }

    /**
     * 发送并获取返回值
     *
     * @param requestPath 请求API地址
     * @param requestJson 请求参数JSON格式
     * @param resultType  返回值类型
     * @return 返回值封装类
     */
    public <T extends InfoResult> Optional<T> get(String requestPath, String requestJson, Class<T> resultType) {
        return get(requestPath, requestJson, res -> {
            // 数据在data下
            JSONObject baseData = JSON.parseObject(res);

            // 判断请求是否获取成功
            CoolQHttpInteractionException.requireNotFailed(baseData);

            // 获取data数据, 并置入原始数据字符串
            Object jsonData = baseData.get("data");
            if (jsonData instanceof JSONObject) {
                // 如果是object类型
                ((JSONObject) jsonData).put("originalData", res);
                return ((JSONObject) jsonData).toJavaObject(resultType);
            } else if (jsonData instanceof JSONArray) {
                // 是数组类型, 将数组放在list字段中并增加originalData字段
                JSONObject newJsonData = new JSONObject(2);
                newJsonData.put("list", jsonData);
                newJsonData.put("originalData", res);
                return newJsonData.toJavaObject(resultType);
            } else if (jsonData == null) {
                // 如果是null，说明没有返回值，赋值为空值
                return JSON.toJavaObject(EMPTY_JSON, resultType);
            } else {
                // 既不是object也不是array， 那能是啥？字符串么？
                // 先抛个异常吧/
                throw new CoolQHttpInteractionException("cannot parse json: " + res);
            }
        });
    }


    public <T extends Result> Optional<T> get(Get<T> get) {
        return get(get.getApi(), get.toJSON(), get.getResultType());
    }

    /**
     * 发送并获取返回值
     *
     * @param requestPath  请求API地址
     * @param requestJson  请求参数JSON格式
     * @param resultGetter 返回值封装类获取函数，参数为请求接口返回的响应字符串
     * @return 返回值封装类
     */
    public <T extends InfoResult> Optional<T> get(String requestPath, String requestJson, Function<String, T> resultGetter) {
        //转化为bean对象，并做防止空指针的处理
        String resultJson = getResultJson(requestPath, requestJson);
        return Optional.ofNullable(resultJson).map(resultGetter);

    }

    /**
     * 获取cookies信息
     *
     * @return cookies
     */
    public QQCookies getCookies() {
        GetCookies in = new GetCookies();
        return get(in.getApi(), JSON.toJSONString(in), in.getResultType()).orElse(null);
    }


    /**
     * 获取csrfToken信息
     *
     * @return csrfToken
     */
    public QQCsrfToken getCsrfToken() {
        GetCsrfToken csrfToken = new GetCsrfToken();
        return get(csrfToken.getApi(), JSON.toJSONString(csrfToken), csrfToken.getResultType()).orElse(null);
    }

    /**
     * 权限信息，其中包含获取Cookie信息和CsrfToken
     *
     * @return
     */
    @Override
    public AuthInfo getAuthInfo() {
        return get(new GetCredentials()).orElse(null);
    }


    /**
     * 不支持的API
     */
    @Override
    public BanList getBanList(String group) {
        return super.getBanList(group);
    }


    /**
     * 不支持的API
     */
    @Override
    public FileInfo getFileInfo(String flag) {
        return super.getFileInfo(flag);
    }

    /**
     * 获取好友列表
     *
     * @return 好友列表
     */
    @Override
    public FriendList getFriendList() {
        // 好友列表格式较为特殊，进行特殊处理
        GetFriendList friendList = new GetFriendList();
        return get(friendList.getApi(), JSON.toJSONString(friendList), res -> {
            // 数据在data下
            JSONObject baseData = JSON.parseObject(res);
            // 判断请求是否获取成功
            CoolQHttpInteractionException.requireNotFailed(baseData);

            // 准备保存
            QQFriendList qqFriendList = new QQFriendList();
            // 首先记录originalData
            qqFriendList.setOriginalData(res);

            // 获取data数据，此处data数据为数组
            JSONArray arrayData = baseData.getJSONArray("data");
            int arrayLength = arrayData.size();
            QQFriendList.QQFriends[] friends = new QQFriendList.QQFriends[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                JSONObject objectData = arrayData.getJSONObject(i);
                QQFriendList.QQFriends thisFriends = new QQFriendList.QQFriends();
                friends[i] = thisFriends;
                // set data
                thisFriends.setFriend_group_id(objectData.getString("friend_group_id"));
                thisFriends.setFriend_group_name(objectData.getString("friend_group_name"));
                // 设置friend的originalData
                JSONArray friendsJsonArray = objectData.getJSONArray("friends");
                int groupFriendSize = friendsJsonArray.size();
                QQFriendList.QQfriend[] friendArray = new QQFriendList.QQfriend[groupFriendSize];
                // set friend array
                thisFriends.setFriends(friendArray);
                for (int k = 0; k < groupFriendSize; k++) {
                    // 当前好友
                    JSONObject friendJSONData = friendsJsonArray.getJSONObject(k);
                    friendArray[k] = friendJSONData.toJavaObject(QQFriendList.QQfriend.class);
                    friendArray[k].setOriginalData(friendJSONData.toJSONString());
                }
            }

            qqFriendList.setFriends(friends);

            return qqFriendList;


        }).orElse(null);
    }


    /**
     * 不支持的API
     */
    @Override
    public GroupHomeworkList getGroupHomeworkList(String group, int number) {
        return super.getGroupHomeworkList(group, number);
    }

    /**
     * 获取群信息
     *
     * @param group 群号
     * @param cache 是否开启缓存
     * @return 群信息
     */
    @Override
    public GroupInfo getGroupInfo(String group, boolean cache) {
        return get(new GetGroupInfo(group, cache)).orElse(null);
    }

    /**
     * 不支持的API
     */
    @Override
    public GroupLinkList getGroupLinkList(String group, int number) {
        return super.getGroupLinkList(group, number);
    }

    /**
     * 获取群列表
     */
    @Override
    public GroupList getGroupList() {
        return get(new GetGroupList()).orElse(null);
    }

    /**
     * 获取群成员信息
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param cache 是否缓存
     * @return 群成员详细信息
     */
    @Override
    public GroupMemberInfo getGroupMemberInfo(String group, String QQ, boolean cache) {
        return get(new GetGroupMemberInfo(group, QQ, cache)).orElse(null);
    }

    /**
     * 获取群成员列表
     *
     * @param group
     * @return
     */
    @Override
    public GroupMemberList getGroupMemberList(String group) {
        // 此请求返回值的数组，需要进行特殊处理。
        return get(new GetGroupMemberList(group)).orElse(null);
    }


    @Override
    public GroupNoteList getGroupNoteList(String group) {
        // 默认使用-1，效率更高
        return getGroupNoteList(group, -1);
    }

    /**
     * 取群公告列表.
     * 参数number对于cqhttpapi来说是不存在的，所以假如大于0，则会通过代码进行截取。
     *
     * @param group  群号
     * @param number 截取数量( 无效参数 ), 如果大于0则截取
     */
    @Override
    public GroupNoteList getGroupNoteList(String group, @Deprecated int number) {
        GetGroupNoticeList in = new GetGroupNoticeList(group);
        return get(in.getApi(), JSON.toJSONString(in), json -> {
            JSONObject baseData = JSONObject.parseObject(json);
            // 判断请求是否获取成功
            CoolQHttpInteractionException.requireNotFailed(baseData);

            // json数据是GroupNotice类型数组
            GroupNoticeList groupNotesList = new GroupNoticeList();
            JSONArray jsonArray = baseData.getJSONArray("data");
            GroupNoticeList.GroupNotice[] array;
            if (number > 0) {
                // 截断并转化为List
                List<Object> list = jsonArray.stream().limit(number).peek(o -> {
                    if (o instanceof JSONObject) {
                        // 添加原生数据值
                        ((JSONObject) o).put("originalData", ((JSON) o).toJSONString());
                    }
                }).collect(Collectors.toList());
                // 赋值
                array = JSONArray.parseArray(JSON.toJSONString(list), GroupNoticeList.GroupNotice.class).toArray(new GroupNoticeList.GroupNotice[0]);
            } else {
                // 截断数值小于1，认为不需要截断，直接转化
                // 相对于大于1时候的频繁转化，小于1的时候不会截断，效率相对更高
                jsonArray.forEach(o -> {
                    if (o instanceof JSONObject) {
                        // 添加原生数据值
                        ((JSONObject) o).put("originalData", ((JSON) o).toJSONString());
                    }
                });
                array = jsonArray.toJavaList(GroupNoticeList.GroupNotice.class).toArray(new GroupNoticeList.GroupNotice[0]);
            }
            // 赋值array
            groupNotesList.setGroupNotices(array);
            // 赋值原始数据JSON字符串， 直接使用返回值字符串
            groupNotesList.setOriginalData(json);
            return groupNotesList;
        }).orElse(null);

    }

    /**
     * 不存在此API，因此调用{@link #getGroupNoteList(String, int)}并获取第一个
     *
     * @param group 群号
     * @return 返回值
     */
    @Override
    public GroupTopNote getGroupTopNote(String group) {
        GroupNoteList groupNoteList = getGroupNoteList(group, 1);
        GroupNote groupNote = groupNoteList.getList()[0];
        return GroupTopNotice.getInstance(groupNote);
    }

    /**
     * 获取图片详细信息
     */
    @Override
    @Deprecated
    public ImageInfo getImageInfo(String flag) {
        GetImage getImage = new GetImage(flag);
        return get(getImage.getApi(), JSON.toJSONString(getImage), getImage.getResultType()).orElse(null);
    }

    /**
     * 获取登录的QQ的信息
     */
    @Override
    public com.forte.qqrobot.bot.LoginInfo getLoginQQInfo() {
        LoginInfo loginInfo = get(new GetLoginInfo()).orElse(null);
        if (loginInfo != null) {
            try {
                QQVipInfo vipInfo = getVipInfo(loginInfo.getUser_id());
                if (vipInfo != null) {
                    loginInfo.setLevel(vipInfo.getLevel());
                }
            } catch (Exception ignored) {
                // vip 信息获取失败
            }
            return loginInfo;
        } else {
            return null;
        }
    }

    /**
     * 获取用户Vip信息
     *
     * @param code QQ号
     */
    public QQVipInfo getVipInfo(String code) {
        return get(new GetVipInfo(code)).orElse(null);
    }

    public QQVipInfo getVipInfo(QQCodeAble codeAble) {
        return getVipInfo(codeAble.getQQCode());
    }


    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public ShareList getShareList(String group) {
        return super.getShareList(group);
    }

    @Override
    public StrangerInfo getStrangerInfo(String QQ, boolean cache) {
        return get(new GetStrangerInfo(QQ, cache)).orElse(null);

    }


    //**************** 发送消息 ****************//

    public <T extends Send> boolean send(T send) {
        try {
            String resultJson = getResultJson(send.getApi(), JSON.toJSONString(send));
            // 判断请求是否获取成功
            CoolQHttpInteractionException.requireNotFailed(JSONObject.parseObject(resultJson));
            return true;
        } catch (Exception e) {
            if (e instanceof CoolQHttpInteractionException) {
                throw (CoolQHttpInteractionException) e;
            } else if (e instanceof RobotRuntimeException) {
                throw (RobotRuntimeException) e;
            } else {
                throw new CoolQHttpInteractionException(e);
            }
        }
    }

    /**
     * 发送消息并得到id返回值
     */
    public <T extends Send> String sendAndId(T send) {
        /*
            发送消息，一般存在两种返回值可能：
            1：返回消息ID
            2：无返回值
            根据目前的接口定义，暂时不处理消息ID的内容
         */
        try {
            String resultJson = getResultJson(send.getApi(), JSON.toJSONString(send));
            // 判断请求是否获取成功
            JSONObject baseData = JSONObject.parseObject(resultJson);
            CoolQHttpInteractionException.requireNotFailed(baseData);
            JSONObject data = baseData.getJSONObject("data");
            if (data != null) {
                return data.getString("message_id");
            } else {
                return null;
            }
        } catch (Exception e) {
            if (e instanceof CoolQHttpInteractionException) {
                throw (CoolQHttpInteractionException) e;
            } else if (e instanceof RobotRuntimeException) {
                throw (RobotRuntimeException) e;
            } else {
                throw new CoolQHttpInteractionException(e);
            }
        }
    }


    /**
     * 发送消息
     *
     * @param group 群号
     * @param msg   消息正文
     */
    @Override
    public String sendDiscussMsg(String group, String msg) {
        // 默认解析CQ码
        return sendAndId(new SendDiscussMsg(group, msg));
    }

    /**
     * 发送讨论组消息
     *
     * @param group      讨论组号
     * @param msg        消息正文
     * @param autoEscape 是否自动解码CQ码
     */
    public String sendDiscussMsg(String group, String msg, boolean autoEscape) {
        return sendAndId(new SendDiscussMsg(group, msg, autoEscape));
    }

    /**
     * 发送群消息
     *
     * @param group 群号
     * @param msg   消息
     * @return
     */
    @Override
    public String sendGroupMsg(String group, String msg) {
        return sendAndId(new SendGroupMsg(group, msg));
    }

    /**
     * 发送群消息
     *
     * @param group      群号
     * @param msg        消息
     * @param autoEscape 是否自动解码CQ码
     * @return
     */
    public String sendGroupMsg(String group, String msg, boolean autoEscape) {
        return sendAndId(new SendGroupMsg(group, msg, autoEscape));
    }

    /**
     * 发送私信
     *
     * @param QQ  QQ号
     * @param msg 正文消息
     */
    @Override
    public String sendPrivateMsg(String QQ, String msg) {
        return sendAndId(new SendPrivateMsg(QQ, msg));
    }

    /**
     * 发送私信
     *
     * @param QQ         QQ号
     * @param msg        正文消息
     * @param autoEscape CQ码自动转码
     */
    public String sendPrivateMsg(String QQ, String msg, boolean autoEscape) {
        return sendAndId(new SendPrivateMsg(QQ, msg, autoEscape));
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public boolean sendFlower(String group, String QQ) {
        return super.sendFlower(group, QQ);
    }

    /**
     * 点赞
     *
     * @param QQ    QQ号
     * @param times 次数，每个好友每天最多10次数，则单次应当必定不可超过10次数
     * @return
     */
    @Override
    public boolean sendLike(String QQ, int times) {
        return send(new SendLike(QQ, times));
    }

    /**
     * 发布群公告
     * 目前，top、toNewMember、confirm参数是无效的
     *
     * @param group       群号
     * @param title       标题
     * @param text        正文
     * @param top         是否置顶，默认false
     * @param toNewMember 是否发给新成员 默认false
     * @param confirm     是否需要确认 默认false
     * @return s是否发布成功
     */
    @Override
    public boolean sendGroupNotice(String group, String title, String text, boolean top, boolean toNewMember, boolean confirm) {
        return send(new SendGroupNotice(group, title, text));
    }


    //**************** SETTER ****************//


    /**
     * 是否同意好友申请
     *
     * @param flag       请求的标识
     * @param friendName 备注
     * @param agree      是否同意
     */
    @Override
    public boolean setFriendAddRequest(String flag, String friendName, boolean agree) {
        return send(new SetFriendAddRequest(flag, friendName, agree));
    }

    /**
     * 处理加群请求
     *
     * @param flag        唯一标识
     * @param requestType 请求类型
     * @param agree       是否同意
     * @param why         如果拒绝，为什么拒绝
     */
    @Override
    public boolean setGroupAddRequest(String flag, GroupAddRequestType requestType, boolean agree, String why) {
        return send(new SetGroupAddRequest(flag, requestType, agree, why));
    }

    /**
     * 设置群管理员
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param set   是否设置为管理员
     */
    @Override
    public boolean setGroupAdmin(String group, String QQ, boolean set) {
        return send(new SetGroupAdmin(group, QQ, set));
    }

    /**
     * 是否允许群匿名聊天
     *
     * @param group 群号
     * @param agree 是否同意
     */
    @Override
    public boolean setGroupAnonymous(String group, boolean agree) {
        return send(new SetGroupAnonymous(group, agree));
    }

    /**
     * 设置群匿名禁言
     *
     * @param group 群号
     * @param flag  唯一标识
     * @param time  时长
     */
    @Override
    public boolean setGroupAnonymousBan(String group, String flag, long time) {
        return send(new SetGroupAnonymousBan(group, flag, time));
    }

    /**
     * 设置群匿名禁言
     *
     * @param group 群号
     * @param flag  唯一标识对象
     * @param time  时长
     */
    public boolean setGroupAnonymousBan(String group, Anonymous flag, long time) {
        return send(new SetGroupAnonymousBan(group, flag, time));
    }

    /**
     * 设置群禁言
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param time  禁言时长
     */
    @Override
    public boolean setGroupBan(String group, String QQ, long time) {
        return send(new SetGroupBan(group, QQ, time));
    }

    /**
     * 设置群标签/昵称
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param card  昵称
     */
    @Override
    public boolean setGroupCard(String group, String QQ, String card) {
        return send(new SetGroupCard(group, QQ, card));
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public boolean setGroupFileDelete(String group, String flag) {
        return super.setGroupFileDelete(group, flag);
    }

    /**
     * 退出讨论组
     *
     * @param group 群号
     */
    @Override
    public boolean setDiscussLeave(String group) {
        return send(new SetDiscussLeave(group));
    }

    /**
     * 退出群组
     *
     * @param group    群号
     * @param dissolve 假如此账号是群主，则此参数代表是否要解散群。默认为false
     */
    @Override
    public boolean setGroupLeave(String group, boolean dissolve) {
        return send(new SetGroupLeave(group, dissolve));
    }

    /**
     * 群主解散群组
     *
     * @param group 群号
     */
    public boolean setGroupLeaveIfGroupOwner(String group) {
        return setGroupLeave(group, true);
    }

    /**
     * 踢出群员
     *
     * @param group    群号
     * @param QQ       QQ号
     * @param dontBack 是否不允许他再回来了
     */
    @Override
    public boolean setGroupMemberKick(String group, String QQ, boolean dontBack) {
        return send(new SetGroupKick(group, QQ, dontBack));
    }

    /**
     * 群签到
     *
     * @param group 群号
     */
    @Override
    @Deprecated
    public boolean setGroupSign(String group) {
        return super.setGroupSign(group);
    }

    /**
     * 设置群成员专属头衔
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param title 头衔
     * @param time  标记时长
     */
    @Override
    public boolean setGroupExclusiveTitle(String group, String QQ, String title, long time) {
        return send(new SetGroupSpecialTitle(group, QQ, title, time));
    }

    /**
     * 设置群成员专属头衔
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param title 头衔
     */
    public boolean setGroupExclusiveTitle(String group, String QQ, String title) {
        return send(new SetGroupSpecialTitle(group, QQ, title));
    }

    /**
     * 设置全群禁言
     *
     * @param group 群号
     * @param in    QQ
     */
    @Override
    public boolean setGroupWholeBan(String group, boolean in) {
        return send(new SetGroupWholeBan(group, in));
    }

    /**
     * 消息撤回 似乎只需要一个消息ID即可
     * 需要pro
     *
     * @param flag 标识
     */
    @Override
    public boolean setMsgRecall(String flag) {
        return send(new SendDeleteMsg(flag));
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public boolean setSign() {
        return super.setSign();
    }
}
