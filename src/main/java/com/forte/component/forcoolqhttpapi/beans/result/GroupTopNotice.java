package com.forte.component.forcoolqhttpapi.beans.result;

import com.forte.qqrobot.beans.messages.result.GroupTopNote;
import com.forte.qqrobot.beans.messages.result.inner.GroupNote;

/**
 * 群置顶公告
 * 由于不存在此API，则获取群公告列表的第一个
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class GroupTopNotice extends GroupNoticeList.GroupNotice implements GroupTopNote {

    /**
     * 通过GroupNotice获取实例对象
     */
    public static GroupTopNote getInstance(GroupNoticeList.GroupNotice in) {
        GroupTopNotice top = new GroupTopNotice();
        top.setOriginalData(in.getOriginalData());
        top.setCn(in.getCn());
        top.setFid(in.getFid());
        top.setFn(in.getFn());
        top.setPubt(in.getPubt());
        top.setRead_num(in.getRead_num());
        top.setSettings(in.getSettings());
        top.setU(in.getU());
        top.setVn(in.getVn());
        top.setMsg(in.getMsgs());
        return top;
    }

    /**
     * 通过GroupNotice获取实例对象
     */
    public static GroupTopNote getInstance(GroupNote in) {
        if (in instanceof GroupNoticeList.GroupNotice) {
            return getInstance((GroupNoticeList.GroupNotice) in);
        } else {
            GroupTopNotice top = new GroupTopNotice();
            top.setOriginalData(in.getOriginalData());
            top.setCn(in.getId());
            top.setFid(null);
            top.setFn(null);
            top.setPubt(in.getTime() + "");
            top.setRead_num(in.getReadNum());
            Settings settings = new Settings();
            settings.setIs_show_edit_card(in.isShowEditCard() ? 0 : 1);
            top.setSettings(settings);
            top.setU(in.getQQ());
            top.setVn(in.getTypeId());
            Msg msg = new Msg();
            msg.setText_face(in.getFaceMsg());
            msg.setTitle(in.getTitle());
            msg.setText(in.getMsg());
            top.setMsg(msg);
            return top;
        }
    }

}
