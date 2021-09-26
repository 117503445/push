package com.wizzstudio.push.controller;

import com.wizzstudio.push.config.StaticFactory;
import com.wizzstudio.push.model.CommonResult;
import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CallbackController {


    @GetMapping("/callback")
    public String verify(
            @RequestParam(name = "msg_signature") String signature,
            @RequestParam(name = "timestamp") String timestamp,
            @RequestParam(name = "nonce") String nonce,
            @RequestParam(name = "echostr") String echoStr
    ) {
        String decrypt = new WxCryptUtil(WechatService.getWxCpService().getWxCpConfigStorage().getToken(),
                WechatService.getWxCpService().getWxCpConfigStorage().getAesKey(),
                WechatService.getWxCpService().getWxCpConfigStorage().getCorpId()).decrypt(echoStr);
        return decrypt;

    }

    @PostMapping("/callback")
    public CommonResult callback(@RequestBody String requestBody,
                                 @RequestParam(name = "msg_signature") String signature,
                                 @RequestParam(name = "timestamp") String timestamp,
                                 @RequestParam(name = "nonce") String nonce) {
        System.out.println("通讯录变更请求过来了");
        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(requestBody, WechatService.getWxCpService().getWxCpConfigStorage(),
                timestamp, nonce, signature);

        String userId = inMessage.getUserId();

        String text = String.format("%s 你好,你可以通过访问 https://risingsun.pro:8088/push/text/v1?name=%s&text=hello 向微信发送通知,更多使用方法请查看文档", userId, userId);

        WxCpMessage message = WxCpMessage.TEXT().agentId(StaticFactory.getWeChatConfig().getAgentId()).toUser(userId).content(text).build();
        try {
            System.out.println("通讯录变更准备发消息");
            WechatService.getWxCpService().getMessageService().send(message);
            System.out.println("通讯录变更发完消息了");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("通讯录变更返回成功给微信");
        return CommonResult.ok();
    }
}

