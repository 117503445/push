package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
import com.wizzstudio.push.model.MsgType;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutTextMessage;
import me.chanjar.weixin.cp.message.WxCpMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/12:49
 * @Description: 用户发送文本消息的时候的Handler
 */

@Component
public class TextHandler implements WxCpMessageHandler {

    @Autowired
    private OutTextMessageBuilder builder;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
        System.out.println("进入textHandler");
        String content = wxCpXmlMessage.getContent();
        String reply = "测试回复:"+content;
        WxCpXmlOutMessage outMessage = builder.build(reply, wxCpXmlMessage, wxCpService);
        System.out.println("返回接收文本消息的outMessage");
        return outMessage;
    }

}
