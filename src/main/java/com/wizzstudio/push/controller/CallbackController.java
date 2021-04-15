package com.wizzstudio.push.controller;

import com.wizzstudio.push.config.FileConfig;
import com.wizzstudio.push.model.CommonResult;
import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
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
        return new WxCryptUtil(WechatService.getWxCpService().getWxCpConfigStorage().getToken(), WechatService.getWxCpService().getWxCpConfigStorage().getAesKey(), WechatService.getWxCpService().getWxCpConfigStorage().getCorpId()).decrypt(echoStr);
    }


    @PostMapping("/callback")
    public CommonResult callback(@RequestBody String requestBody,
                                 @RequestParam(name = "msg_signature") String signature,
                                 @RequestParam(name = "timestamp") String timestamp,
                                 @RequestParam(name = "nonce") String nonce) {

        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(requestBody, WechatService.getWxCpService().getWxCpConfigStorage(),
                timestamp, nonce, signature);

        var userId = inMessage.getUserId();

        var text = String.format("%s 你好,你可以通过访问 https://push.gh.117503445.top:20000/push/text/v1?name=%s&text=hello 向微信发送通知,更多使用方法请查看文档", userId, userId);

        WxCpMessage message = WxCpMessage.TEXT().agentId(FileConfig.getAgentId()).toUser(userId).content(text).build();
        try {
            WechatService.getWxCpService().getMessageService().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return CommonResult.Success();
    }
}

