package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
import com.wizzstudio.push.model.ReplyDTO;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.message.WxCpMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/20:56
 * @Description: 启动昵称事件的handler
 */
@Component
public class NicknameEnableHandler implements WxCpMessageHandler {
    @Autowired
    private OutTextMessageBuilder textMessageBuilder;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) {
        ReplyDTO replyDTO = new ReplyDTO(wxCpXmlMessage.getFromUserName(),wxCpXmlMessage.getToUserName(),"点击事件" + wxCpXmlMessage.getEventKey());
        WxCpXmlOutMessage outMessage = textMessageBuilder.build(replyDTO);
        return outMessage;
    }
}
