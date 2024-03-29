package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
import com.wizzstudio.push.exception.WxUserException;
import com.wizzstudio.push.model.ReplyDTO;
import com.wizzstudio.push.service.UserService;
import com.wizzstudio.push.utils.TextUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.message.WxCpMessageHandler;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/20:57
 * @Description: 禁用昵称事件的handler
 */
@Component
public class NicknameDisableHandler implements WxCpMessageHandler {

    @Autowired
    private OutTextMessageBuilder textMessageBuilder;

    @Autowired
    private UserService userService;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) {
        //获取用户id
        String userId = wxCpXmlMessage.getFromUserName();
        //设置当前用户处于正在设置禁用昵称的流程中
        boolean setUserStatusDisable = userService.setUserStatusDisable(userId);
        if (!setUserStatusDisable) throw new WxUserException(WxUserException.USER_STATUS_SET_ERROR,"系统繁忙,请您稍后重试",userId);
        //设置回复DTO
        ReplyDTO replyDTO = new ReplyDTO(wxCpXmlMessage.getFromUserName(),wxCpXmlMessage.getToUserName(),null);
        //先获取所有可用的昵称
        List<String> listNicknamesAble = userService.listNicknamesAble(userId);
        if (CollectionUtils.isEmpty(listNicknamesAble)){
            replyDTO.setContent("您没有已启用的昵称");
            //删除用户流程状态
            userService.deleteUserStatus(userId);
        }else {
            //将列表转成文本
            String listContent = TextUtils.convertList(listNicknamesAble);
            replyDTO.setContent("您已启用的昵称如下:\n\n"+listContent+"\n请直接回复您需要禁用的昵称");
        }
        WxCpXmlOutMessage outMessage = textMessageBuilder.build(replyDTO);
        return outMessage;
    }
}
