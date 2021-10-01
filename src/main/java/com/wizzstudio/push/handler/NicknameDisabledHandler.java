package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
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
 * @Date: 2021/09/25/20:56
 * @Description: 点击查看不可用昵称事件的handler
 */
@Component
public class NicknameDisabledHandler implements WxCpMessageHandler {

    @Autowired
    private OutTextMessageBuilder textMessageBuilder;

    @Autowired
    private UserService userService;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) {
        String userId = wxCpXmlMessage.getFromUserName();
        List<String> nicknamesDisabled = userService.listNicknamesDisabled(userId);
        //设置回复信息的DTO
        ReplyDTO replyDTO = new ReplyDTO(userId,wxCpXmlMessage.getToUserName(),null);
        if (CollectionUtils.isEmpty(nicknamesDisabled)){
            replyDTO.setContent("您没有已禁用的昵称");
        }else {
            replyDTO.setContent( "您的已禁用的昵称如下:\n\n" + TextUtils.convertList(nicknamesDisabled));
        }
        //交给返回文本创建类去生成返回的message
        WxCpXmlOutMessage outMessage = textMessageBuilder.build(replyDTO);
        //删除用户流程状态
        userService.deleteUserStatus(userId);
        return outMessage;
    }
}
