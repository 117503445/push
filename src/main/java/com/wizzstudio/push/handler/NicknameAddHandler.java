package com.wizzstudio.push.handler;

import com.wizzstudio.push.builder.OutTextMessageBuilder;
import com.wizzstudio.push.exception.WxUserException;
import com.wizzstudio.push.service.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.message.WxCpMessageHandler;
import org.apache.catalina.User;
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

    @Autowired
    private UserService userService;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
        //设置用户状态为正在添加昵称中
        boolean setUserStatusAdd = userService.setUserStatusAdd(wxCpXmlMessage.getFromUserName());
        if (!setUserStatusAdd) throw new WxUserException(WxUserException.USER_STATUS_SET_ERROR,"系统繁忙,请您稍后重试");
        WxCpXmlOutMessage outMessage = textMessageBuilder.build("请直接回复您需要添加的昵称(由2-12位英文数字组成)", wxCpXmlMessage, wxCpService);
        return outMessage;
    }
}
