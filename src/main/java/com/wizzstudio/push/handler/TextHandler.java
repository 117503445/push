package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
import com.wizzstudio.push.model.MsgType;
import com.wizzstudio.push.model.ReplyDTO;
import com.wizzstudio.push.service.UserService;
import com.wizzstudio.push.utils.TextUtils;
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
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) {
        System.out.println("进入textHandler");
        //获取用户id,也就是收到的消息的发送者,也是回复消息的接收者
        String userId = wxCpXmlMessage.getFromUserName();
        //设置回复DTO,回复的正文等待后续赋值
        ReplyDTO replyDTO = new ReplyDTO( userId, wxCpXmlMessage.getToUserName(),null);
        //当用户有流程状态时,进入流程判断
        if (userService.userStatusExists(userId)){
            int status = userService.getUserStatus(userId);
            //根据用户流程状态进行路由,并接收返回的正文内容并设置进入DTO中
            replyDTO.setContent(routeByStatus(userId, status, wxCpXmlMessage.getContent()));
        }else {
            //无流程时,根据以后的功能在这里增加后续的处理,目前直接返回如下文本
            replyDTO.setContent("其他功能正在积极开发中,敬请期待");
        }
        WxCpXmlOutMessage outMessage = builder.build(replyDTO);
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
