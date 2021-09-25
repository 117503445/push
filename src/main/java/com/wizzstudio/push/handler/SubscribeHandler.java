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
 * @Date: 2021/09/25/12:23
 * @Description: 用户关注事件的handler
 */

@Component
public class SubscribeHandler implements WxCpMessageHandler {

    @Autowired
    OutTextMessageBuilder builder;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
        System.out.println("接收到了关注事件的请求");
        String userName = wxCpXmlMessage.getFromUserName();
        String content = String.format("%s :\n你好,欢迎使用wizz企业微信推送服务,你可以参考如果例子进行消息推送,http://49.234.111.177:8088/push/text/v1?name=%s&text=hello",userName,userName);
        WxCpXmlOutMessage outMessage = builder.build(content, wxCpXmlMessage, wxCpService);
        System.out.println("关注事件返回outMessage");
        return outMessage;
    }
}
