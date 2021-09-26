package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
import com.wizzstudio.push.model.MsgType;
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
 * @Date: 2021/09/25/12:49
 * @Description: 用户发送文本消息的时候的Handler
 */

@Component
public class TextHandler implements WxCpMessageHandler {

    @Autowired
    private OutTextMessageBuilder builder;

    @Autowired
    private UserService userService;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
        System.out.println("进入textHandler");
        String reply;
        String userId = wxCpXmlMessage.getFromUserName();
        if (userService.userStatusExists(userId)){
            int status = userService.getUserStatus(userId);
            reply = routeByStatus(userId, status, wxCpXmlMessage.getContent());
        }else {
            return null;
        }
        WxCpXmlOutMessage outMessage = builder.build(reply, wxCpXmlMessage, wxCpService);
        System.out.println("返回接收文本消息的outMessage");
        return outMessage;
    }

    public String routeByStatus(String userId,int status,String nickname){
        String reply = "";
        switch (status){
            case 0 :
                userService.addNickname(userId, nickname);
                reply = "您已成功添加昵称 : "+nickname;
                break;

            case 1 :
                userService.enableNickname(userId, nickname);
                reply = "您已成功启用昵称 : "+nickname;
                break;

            case -1 :
                userService.disableNickname(userId,nickname);
                reply = "您已成功禁用昵称 : "+nickname;
                break;

            default :
                break;
        }
        return reply;
    }




}
