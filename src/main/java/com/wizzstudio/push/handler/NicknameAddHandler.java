package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
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
 * @Date: 2021/09/25/14:03
 * @Description: 菜单点击事件的handler
 */

@Component
public class NicknameAddHandler implements WxCpMessageHandler {

    @Autowired
    private OutTextMessageBuilder textMessageBuilder;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
        WxCpXmlOutMessage outMessage = textMessageBuilder.build("点击事件" + wxCpXmlMessage.getEventKey(), wxCpXmlMessage, wxCpService);
        return outMessage;
    }
}
