package com.wizzstudio.push.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.message.WxCpMessageHandler;

import java.util.Map;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/20:56
 * @Description: 启动昵称事件的handler
 */
public class NicknameEnableHandler implements WxCpMessageHandler {
    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map, WxCpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
        return null;
    }
}
