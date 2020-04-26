package com.forte.component.forcoolqhttpapi.beans.msg;

import com.forte.qqrobot.beans.messages.FlagAble;
import com.forte.qqrobot.beans.messages.msgget.GroupFileUpload;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群文件上传事件
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Getter
@Setter
@ToString
@MsgOn(type = PostType.notice, messageType = QQGroupFileUpload.NOTICE_TYPE)
public class QQGroupFileUpload extends BaseMsg implements GroupFileUpload {
    /*
        字段名	数据类型	可能的值	说明
        post_type	string	notice	上报类型
        notice_type	string	group_upload	通知类型
        group_id	number (int64)	-	群号
        user_id	number (int64)	-	发送者 QQ 号
        file	object	-	文件信息
     */

    public static final PostType POST_TYPE = PostType.notice;
    public static final String NOTICE_TYPE = "group_upload";

    private String group_id;
    private String user_id;
    private FileInfo file;

    @Override
    public String getGroup() {
        return group_id;
    }

    @Override
    public String getQQ() {
        return user_id;
    }

    @Override
    public String getFileName() {
        return file == null ? null : file.name;
    }

    @Override
    public Long getFileSize() {
        return file == null ? null : file.size;
    }

    @Override
    public String getFileBusid() {
        return file == null ? null : String.valueOf(file.busid);
    }

    @Override
    public String getId() {
        return file == null ? null : file.id;
    }


    /**
     * 字段 file封装类
     */
    @Getter
    @Setter
    @ToString
    public static class FileInfo implements FlagAble {
        /*
            其中 file 字段的内容如下：

            字段名	数据类型	说明
            id	string	文件 ID
            name	string	文件名
            size	number (int64)	文件大小（字节数）
            busid	number (int64)	busid（目前不清楚有什么作用）
         */

        private String id;
        private String name;
        private Long size;
        private Long busid;

        @Override
        public String getFlag() {
            return id;
        }
    }

}
