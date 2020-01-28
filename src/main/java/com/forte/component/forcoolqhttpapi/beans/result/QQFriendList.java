package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.result.FriendList;
import com.forte.qqrobot.beans.messages.result.inner.Friend;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 好友列表
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@ToString
public class QQFriendList implements FriendList, Result {

    /*
        响应数据
        当 flat 为 false 时，响应内容为 JSON 数组，每个元素为一个好友分组，格式如下：

        字段名	数据类型	说明
        friend_group_id	number	好友分组 ID
        friend_group_name	string	好友分组名称
        friends	array	分组中的好友
        friends[i].nickname	string	好友昵称
        friends[i].remark	string	好友备注
        friends[i].user_id	number	好友 QQ 号

        当 flat 为 true 时，响应内容为 JSON 对象，如下：

        字段名	数据类型	说明
        friend_groups	array	好友分组列表
        friend_groups[i].friend_group_id	number	好友分组 ID
        friend_groups[i].friend_group_name	string	好友分组名称
        friends	array	好友列表
        friends[i].nickname	string	好友昵称
        friends[i].remark	string	好友备注
        friends[i].user_id	number	好友 QQ 号
        friends[i].friend_group_id	number	好友所在分组 ID

     */
    /* ———————— 根据 flat 为false的情况来获取 ———————— */

    /**
     * 首先，原始数据
     */
    @Getter
    @Setter
    private String originalData;

    /**
     * 好友列表
     */
    @Getter
    @Setter
    private QQFriends[] friends;

    /**
     * 真正的分组，在获取一次之后才实例化
     */
    private Map<String, Friend[]> realFriendList;

    @Override
    public Map<String, Friend[]> getFriendList() {
        // if null, instantiation
        if (realFriendList == null) {
            // 根据groupId排序
            realFriendList = new LinkedHashMap<>(8);
            if (friends != null) {
                Arrays.sort(friends);
                for (QQFriends fs : friends) {
                    realFriendList.merge(
                            fs.friend_group_name,
                            fs.friends == null ? new QQfriend[0] : fs.friends,
                            (old, val) -> Stream.concat(Arrays.stream(old), Arrays.stream(val)).toArray(Friend[]::new));
                }
            }
        }
        return realFriendList;
    }

    /**
     * 分组使用组名而不是组ID
     *
     * @param group 分组名称
     */
    @Override
    public Friend[] getFirendList(String group) {
        return getFriendList().get(group);
    }


    /**
     * 好友列表, 用于保存返回值数组
     */
    @ToString
    public static class QQFriends implements Comparable<QQFriends> {
        /*
        friend_group_id	number	好友分组 ID
        friend_group_name	string	好友分组名称
        friends	array	分组中的好友
         */
        @Getter@Setter
        private String friend_group_id;
        @Getter@Setter
        private String friend_group_name;
        @Getter@Setter
        private QQfriend[] friends;

        private Integer sortId;

        public Integer getSortId() {
            if(sortId == null){
                try {
                    sortId = Integer.parseInt(friend_group_id);
                }catch (Exception e){
                    sortId = 0;
                }
            }
            return sortId;
        }

        @Override
        public int compareTo(QQFriends o) {
            return Integer.compare(sortId, o.sortId);
        }
    }

    /**
     * QQ好友
     */
    @Getter
    @Setter
    public static class QQfriend implements Friend {
        /*
        friends[i].nickname	string	好友昵称
        friends[i].remark	string	好友备注
        friends[i].user_id	number	好友 QQ 号
         */
        private String nickname;
        private String remark;
        private String user_id;
        private String originalData;

        @Override
        public String toString(){
            return nickname + "(" + user_id + ")";
        }

        @Override
        public String getName() {
            return nickname;
        }

        @Override
        public String getQQ() {
            return user_id;
        }
    }

}
