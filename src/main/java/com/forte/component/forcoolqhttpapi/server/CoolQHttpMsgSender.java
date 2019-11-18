package com.forte.component.forcoolqhttpapi.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.component.forcoolqhttpapi.CoolQHttpConfiguration;
import com.forte.component.forcoolqhttpapi.CoolQHttpInteractionException;
import com.forte.component.forcoolqhttpapi.CoolQHttpResourceDispatchCenter;
import com.forte.component.forcoolqhttpapi.beans.get.*;
import com.forte.component.forcoolqhttpapi.beans.msg.Anonymous;
import com.forte.component.forcoolqhttpapi.beans.result.*;
import com.forte.component.forcoolqhttpapi.beans.send.*;
import com.forte.component.forcoolqhttpapi.beans.set.*;
import com.forte.qqrobot.beans.messages.result.GroupList;
import com.forte.qqrobot.beans.messages.result.GroupMemberList;
import com.forte.qqrobot.beans.messages.result.StrangerInfo;
import com.forte.qqrobot.beans.messages.result.*;
import com.forte.qqrobot.beans.messages.result.inner.GroupNote;
import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.senderlist.BaseRootSenderList;
import com.forte.qqrobot.utils.HttpClientUtil;
import com.forte.qqrobot.utils.proxyhelper.JSONParameterCreatorHelper;

import java.util.List;
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

    /**
     * json获取器
     */
    private SendJsonCreator json;

    /**
     * 构造，提供一个Json获取器
     */
    public CoolQHttpMsgSender(SendJsonCreator creator) {
        this.json = creator;
    }

    /**
     * 直接通过代理工具创建json获取器
     */
    public CoolQHttpMsgSender() {
        this(JSONParameterCreatorHelper.toJsonParameterCreator(SendJsonCreator.class));
    }

    /**
     * 请求接口，并返回原生返回值字符串
     * @return 原生返回值字符串
     * @throws Exception 可能会出现请求返回值错误
     */
    private String getResultJson(String requestPath, String requestJson) throws Exception {
        //获取HTTP API请求地址参数
        CoolQHttpConfiguration httpConfiguration = CoolQHttpResourceDispatchCenter.getCoolQHttpConfiguration();
        String url = httpConfiguration.getRequestPath() + requestPath;
        //请求并返回响应数据
        return HttpClientUtil.post(url, requestJson);
    }

    /**
     * 发送并获取返回值
     * @param requestPath   请求API地址
     * @param requestJson   请求参数JSON格式
     * @param resultType    返回值类型
     * @return  返回值封装类
     */
    public <T extends InfoResult> Optional<T> get(String requestPath, String requestJson, Class<T> resultType) {
        return get(requestPath, requestJson, res -> {
            // 数据在data下
            JSONObject baseData = JSON.parseObject(res);

            // 判断请求是否获取成功
            CoolQHttpInteractionException.requireNotFailed(baseData);

            // 获取data数据, 并置入原始数据字符串
            JSONObject jsonObject = baseData.getJSONObject("data");
            jsonObject.put("originalData", res);
            return jsonObject.toJavaObject(resultType);
        });
    }


    public <T extends Result> Optional<T> get(Get<T> get){
            return get(get.getApi(), JSON.toJSONString(get), get.getResultType());
    }

    /**
     * 发送并获取返回值
     * @param requestPath   请求API地址
     * @param requestJson   请求参数JSON格式
     * @param resultGetter  返回值封装类获取函数，参数为请求接口返回的响应字符串
     * @return  返回值封装类
     */
    public <T extends InfoResult> Optional<T> get(String requestPath, String requestJson, Function<String, T> resultGetter) {
        try {
            //转化为bean对象，并做防止空指针的处理
            return Optional.ofNullable(getResultJson(requestPath, requestJson)).map(resultGetter);
        } catch (Exception e) {
            QQLog.error("信息获取失败!", e);
            return Optional.empty();
        }

    }

//    @Override
//    public AnonInfo getAnonInfo(String flag) {
//        不支持的API
//        return null;
//    }

    /**
     * 获取cookies信息
     * @return
     */
    public QQCookies getCookies(){
        GetCookies in = json.getCookies();
        return get(in.getApi(), JSON.toJSONString(in), in.getResultType()).orElse(null);
    }

    /**
     * 权限信息，其中包含获取Cookie信息和CsrfToken
     * @return
     */
    @Override
    public AuthInfo getAuthInfo() {
        // 权限信息中，存在cookie和
        GetCookies cookies = json.getCookies();
        String qqCookies = get(cookies.getApi(), JSON.toJSONString(cookies), cookies.getResultType())
                .map(QQCookies::getCookies)
                .orElse(null);

        // 和csrfToken
        GetCsrfToken csrfToken = json.getCsrfToken();
        String qqCsrfToken = get(csrfToken.getApi(), JSON.toJSONString(csrfToken), csrfToken.getResultType())
                .map(QQCsrfToken::getToken)
                .orElse(null);

        CoolQAuthInfo coolQAuthInfo = new CoolQAuthInfo();
        coolQAuthInfo.setCookies(qqCookies);
        coolQAuthInfo.setCsrfToken(qqCsrfToken);
        coolQAuthInfo.setOriginalData(JSON.toJSONString(coolQAuthInfo));

        return coolQAuthInfo;

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
     * @return 好友列表
     */
    @Override
    public FriendList getFriendList() {
        return get(new GetFriendList()).orElse(null);
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
     * @param group 群号
     * @param cache 是否开启缓存
     * @return  群信息
     */
    @Override
    public GroupInfo getGroupInfo(String group, boolean cache) {
        return get(new GetGroupInfo(group)).orElse(null);
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
     * @param group 群号
     * @param QQ    QQ号
     * @param cache 是否缓存
     * @return  群成员详细信息
     */
    @Override
    public GroupMemberInfo getGroupMemberInfo(String group, String QQ, boolean cache) {
        return get(new GetGroupMemberInfo(group, QQ, cache)).orElse(null);
    }

    /**
     * 获取群成员列表
     * @param group
     * @return
     */
    @Override
    public GroupMemberList getGroupMemberList(String group) {
        return get(new GetGroupMemberList(group)).orElse(null);
    }


    @Override
    public GroupNoteList getGroupNoteList(String group){
        // 默认使用-1，效率更高
        return getGroupNoteList(group, -1);
    }

    /**
     * 取群公告列表
     * @param group     群号
     * @param number    截取数量( 无效参数 ), 如果大于0则截取
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
            if(number > 0){
                // 截断并转化为List
                List<Object> list = jsonArray.stream().limit(number).peek(o -> {
                    if (o instanceof JSONObject) {
                        // 添加原生数据值
                        ((JSONObject) o).put("originalData", ((JSON) o).toJSONString());
                    }
                }).collect(Collectors.toList());
                // 赋值
                array = JSONArray.parseArray(JSON.toJSONString(list), GroupNoticeList.GroupNotice.class).toArray(new GroupNoticeList.GroupNotice[0]);
            }else{
                // 截断数值小于1，认为不需要截断，直接转化
                // 相对于大于1时候的频繁转化，小于1的时候不会截断，效率相对更高
                jsonArray.forEach(o -> {
                    if(o instanceof JSONObject){
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
     * @param group 群号
     * @return  返回值
     */
    @Override
    public GroupTopNote getGroupTopNote(String group) {
        GroupNoteList groupNoteList = getGroupNoteList(group, 1);
        GroupNote groupNote = groupNoteList.getList()[0];
        return GroupTopNotice.getInstance(groupNote);
    }

    /**
     * 不支持的API
     */
    @Override
    @Deprecated
    public ImageInfo getImageInfo(String flag) {
        return super.getImageInfo(flag);
    }

    /**
     * 获取登录的QQ的信息
     */
    @Override
    public LoginQQInfo getLoginQQInfo() {
        return get(new GetLoginInfo()).orElse(null);
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

    /**
     * 发送消息
     */
    public <T extends Send> boolean send(T send){
        /*
            发送消息，一般存在两种返回值可能：
            1：返回消息ID
            2：无返回值
            根据目前的接口定义，暂时不处理消息ID的内容
         */

        try {
            String resultJson = getResultJson(send.getApi(), JSON.toJSONString(send));
            // 判断请求是否获取成功
            CoolQHttpInteractionException.requireNotFailed(JSONObject.parseObject(resultJson));
            return true;
        }catch (Exception e){
            if(e instanceof CoolQHttpInteractionException){
                throw (CoolQHttpInteractionException) e;
            }
            return false;
        }
    }


    /**
     * 发送消息
     * @param group 群号
     * @param msg   消息正文
     */
    @Override
    public boolean sendDiscussMsg(String group, String msg) {
        // 默认解析CQ码
        return send(new SendDiscussMsg(group, msg));
    }

    /**
     * 发送讨论组消息
     * @param group      讨论组号
     * @param msg        消息正文
     * @param autoEscape 是否自动解码CQ码
     */
    public boolean sendDiscussMsg(String group, String msg, boolean autoEscape){
        return send(new SendDiscussMsg(group, msg, autoEscape));
    }

    /**
     * 发送群消息
     * @param group 群号
     * @param msg   消息
     * @return
     */
    @Override
    public boolean sendGroupMsg(String group, String msg) {
        return send(new SendGroupMsg(group, msg));
    }

    /**
     * 发送群消息
     * @param group 群号
     * @param msg   消息
     * @param autoEscape 是否自动解码CQ码
     * @return
     */
    public boolean sendGroupMsg(String group, String msg, boolean autoEscape) {
        return send(new SendGroupMsg(group, msg, autoEscape));
    }

    /**
     * 发送私信
     * @param QQ    QQ号
     * @param msg   正文消息
     */
    @Override
    public boolean sendPrivateMsg(String QQ, String msg) {
        return send(new SendPrivateMsg(QQ, msg));
    }

    /**
     * 发送私信
     * @param QQ    QQ号
     * @param msg   正文消息
     * @param autoEscape CQ码自动转码
     */
    public boolean sendPrivateMsg(String QQ, String msg, boolean autoEscape) {
        return send(new SendPrivateMsg(QQ, msg, autoEscape));
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
     * @param QQ    QQ号
     * @param times 次数，每个好友每天最多10次数，则单次应当必定不可超过10次数
     * @return
     */
    @Override
    public boolean sendLike(String QQ, int times) {
        return send(new SendLike(QQ, times));
    }

    /**
     * 是否同意好友申请
     * @param flag          请求的标识
     * @param friendName    备注
     * @param agree         是否同意
     */
    @Override
    public boolean setFriendAddRequest(String flag, String friendName, boolean agree) {
        return send(new SetFriendAddRequest(flag, friendName, agree));
    }

    /**
     * 处理加群请求
     * @param flag          唯一标识
     * @param requestType   请求类型
     * @param agree         是否同意
     * @param why           如果拒绝，为什么拒绝
     */
    @Override
    public boolean setGroupAddRequest(String flag, GroupAddRequestType requestType, boolean agree, String why) {
        return send(new SetGroupAddRequest(flag, requestType, agree, why));
    }

    /**
     * 设置群管理员
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
     * @param group 群号
     * @param agree 是否同意
     */
    @Override
    public boolean setGroupAnonymous(String group, boolean agree) {
        return send(new SetGroupAnonymous(group, agree));
    }

    /**
     * 设置群匿名禁言
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
     * @param group 群号
     * @param flag  唯一标识对象
     * @param time  时长
     */
    public boolean setGroupAnonymousBan(String group, Anonymous flag, long time) {
        return send(new SetGroupAnonymousBan(group, flag, time));
    }

    /**
     * 设置群禁言
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
     * @param group 群号
     */
    @Override
    public boolean setDiscussLeave(String group) {
        return send(new SetDiscussLeave(group));
    }

    /**
     * 退出群组
     * @param group 群号
     */
    @Override
    public boolean setGroupLeave(String group) {
        return send(new SetGroupLeave(group));
    }

    /**
     * 群主解散群组
     * @param group 群号
     */
    public boolean setGroupLeaveIfGroupOwner(String group) {
        return send(new SetGroupLeave(group, true));
    }

    /**
     * 踢出群员
     * @param group     群号
     * @param QQ        QQ号
     * @param dontBack  是否不允许他再回来了
     */
    @Override
    public boolean setGroupMemberKick(String group, String QQ, boolean dontBack) {
        return send(new SetGroupKick(group, QQ, dontBack));
    }

    /**
     * 群签到
     * 貌似不支持的API
     * @param group 群号
     */
    @Override
    @Deprecated
    public boolean setGroupSign(String group) {
        return super.setGroupSign(group);
    }

    /**
     * 设置群成员专属头衔
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
     * @param group 群号
     * @param QQ    QQ号
     * @param title 头衔
     */
    public boolean setGroupExclusiveTitle(String group, String QQ, String title) {
        return send(new SetGroupSpecialTitle(group, QQ, title));
    }

    /**
     * 设置全群禁言
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
