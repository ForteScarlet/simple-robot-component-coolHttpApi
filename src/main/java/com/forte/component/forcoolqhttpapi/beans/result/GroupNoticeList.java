package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.result.GroupNoteList;
import com.forte.qqrobot.beans.messages.result.inner.GroupNote;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群公告信息
 * 列表类型
 * ※ 需要特殊处理返回值
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
public class GroupNoticeList implements Result, GroupNoteList {

    private GroupNotice[] groupNotices;
    private String originalData;

    @Override
    public GroupNotice[] getList() {
        return new GroupNotice[0];
    }
    /*
        [
            {
                "cn": 0,
                "fid": "3b130f28000000006ef0a95cef090f00",
                "fn": 0,
                "msg": {
                    "text": "喵~&nbsp;喵~",
                    "text_face": "喵~&nbsp;&nbsp;喵~",
                    "title": "喵喵喵"
                },
                "pubt": 1554641006,
                "read_num": 1,
                "settings": {
                    "is_show_edit_card": 0,
                    "remind_ts": 0
                },
                "u": 3281334718,
                "vn": 0
            }
        ]
     */


    /**
     * 群公告
     */
    @ToString
    public static class GroupNotice implements GroupNote {
        @Getter@Setter private String originalData;
        /*
        这里的数据是 QQ 接口返回的原始数据，
        其中，text 和 title 等字段的内容被进行了 HTML 转义（如 &nbsp;）；
        除此之外，还可能会存在一些特殊二进制值，用于表示特殊内容，具体含义可能需要自行理解。
              {
                "cn": 0,
                "fid": "3b130f28000000006ef0a95cef090f00",
                "fn": 0,
                "msg": {
                    "text": "喵~&nbsp;喵~",
                    "text_face": "喵~&nbsp;&nbsp;喵~",
                    "title": "喵喵喵"
                },
                "pubt": 1554641006,
                "read_num": 1,
                "settings": {
                    "is_show_edit_card": 0,
                    "remind_ts": 0
                },
                "u": 3281334718,
                "vn": 0
            }
         */

         @Getter@Setter private String cn;
         @Getter@Setter private String fid;
         @Getter@Setter private String fn;

         private Msg msg;
         @Getter@Setter private String pubt;
         @Getter@Setter private Integer read_num;
         @Getter@Setter private Settings settings;
         @Getter@Setter private String u;
         @Getter@Setter private String vn;

         public Msg getMsgs(){
             return msg;
         }

         public void setMsg(Msg msg){
             this.msg = msg;
         }

        /**
         * 获取消息字符串
         */
         @Override
         public String getMsg(){
             return msg == null ? null : msg.text;
         }

        @Override
        public String getId() {
            return cn;
        }

        @Override
        public String getFaceMsg() {
            return msg == null ? null : msg.text_face;
        }

        @Override
        public String getTitle() {
            return msg == null ? null : msg.title;
        }

        @Override
        public Long getTime() {
            return Long.parseLong(pubt);
        }

        @Override
        public Integer getReadNum() {
            return read_num;
        }

        @Override
        public Boolean isShowEditCard() {
            return settings == null ? null : settings.is_show_edit_card == 0;
        }

        @Override
        public String getQQ() {
            return u;
        }

        @Override
        public String getTypeId() {
            return vn;
        }


        /**
         * Msg封装类 {@link GroupNotice} 字段中的Msg对象
         */
        @Setter@Getter@ToString
        public static class Msg{
            /*
                    "text": "喵~&nbsp;喵~",
                    "text_face": "喵~&nbsp;&nbsp;喵~",
                    "title": "喵喵喵"
             */

            private String text;
            private String text_face;
            private String title;
        }

        @Setter@Getter@ToString
        public static class Settings{
            /*
                    "is_show_edit_card": 0,
                    "remind_ts": 0
             */
            private Integer is_show_edit_card;
            private String remind_ts;

        }



    }
}
