package com.wizzstudio.push.config;

import com.wizzstudio.push.handler.*;
import com.wizzstudio.push.model.EventKey;
import com.wizzstudio.push.model.EventType;
import com.wizzstudio.push.model.MsgType;
import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/12:13
 * @Description: 配置接收消息的路由规则
 */

@Configuration
public class WxMessageRouterConfig {

    @Autowired
    private SubscribeHandler subscribeHandler;

    @Autowired
    private TextHandler textHandler;

    @Autowired
    private NicknameAddHandler clickHandler;

    @Autowired
    private NicknameAbleHandler ableHandler;

    @Autowired
    private NicknameDisabledHandler disabledHandler;

    @Autowired
    private NicknameDisableHandler disableHandler;

    @Autowired
    private NicknameEnableHandler enableHandler;

    @Bean
    public WxCpMessageRouter router(){
        //获得企业微信Service对象
        WxCpService wxCpService = WechatService.getWxCpService();
        //获取微信配置信息
        WxCpConfigStorage wxCpConfig = wxCpService.getWxCpConfigStorage();
        //创建WxMessageRouter对象
        WxCpMessageRouter router = new WxCpMessageRouter(wxCpService);
        router.rule().msgType(MsgType.EVENT).async(false).event(EventType.SUBSCRIBE).handler(subscribeHandler).end();//配置用户关注事件的路由
        router.rule().msgType(MsgType.TEXT).async(false).handler(textHandler).end();//配置用户发送文本消息的路由
        router.rule().msgType(MsgType.EVENT).async(false).event(EventType.CLICK).eventKey(EventKey.NICKNAME_ABLE).handler(ableHandler).end();//配置用户点击查看可用昵称事件的路由
        router.rule().msgType(MsgType.EVENT).async(false).event(EventType.CLICK).eventKey(EventKey.NICKNAME_DISABLED).handler(disabledHandler).end();//配置用户查看已禁用昵称事件的路由
        router.rule().msgType(MsgType.EVENT).async(false).event(EventType.CLICK).eventKey(EventKey.NICKNAME_ADD).handler(ableHandler).end();//配置用户添加新昵称事件的路由
        router.rule().msgType(MsgType.EVENT).async(false).event(EventType.CLICK).eventKey(EventKey.NICKNAME_ENABLE).handler(enableHandler).end();//配置用户启用昵称事件的路由
        router.rule().msgType(MsgType.EVENT).async(false).event(EventType.CLICK).eventKey(EventKey.NICKNAME_DISABLE).handler(disableHandler).end();//配置用户禁用昵称事件的路由
        return router;
    }

}
