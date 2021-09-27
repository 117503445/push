package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
import com.wizzstudio.push.model.ReplyDTO;
import com.wizzstudio.push.service.UserService;
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

    @Autowired
    private UserService userService;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) {
        System.out.println("接收到了关注事件的请求");
        String userId = wxCpXmlMessage.getFromUserName();
        String firstNickname = userService.initUser(userId);
        String content = String.format("%s :\n你好,欢迎使用wizz-studio企业微信推送服务,你可以参考如下例子进行消息推送,http://49.234.111.177:8088/push/text/v1?name=%s&text=hello\nurl中的name是您的昵称" +
                ",初始昵称为您的用户名,可以通过菜单对昵称进行管理,建议每个服务用不同的昵称进行区别",firstNickname,firstNickname);
        ReplyDTO replyDTO = new ReplyDTO(wxCpXmlMessage.getFromUserName(),wxCpXmlMessage.getToUserName(),content);
        WxCpXmlOutMessage outMessage = builder.build(replyDTO);
        System.out.println("关注事件返回outMessage");
        return outMessage;
    }
}
