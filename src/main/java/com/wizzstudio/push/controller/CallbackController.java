package com.wizzstudio.push.controller;

import com.wizzstudio.push.config.FileConfig;
import com.wizzstudio.push.model.CardDTO;
import com.wizzstudio.push.model.CommonResult;
import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
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

        WxCpMessage message = WxCpMessage.TEXT().agentId(FileConfig.getAgentId()).toUser(inMessage.getUserId()).content("Hello" + inMessage.getUserId()).build();
        try {
            WechatService.getWxCpService().getMessageService().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return CommonResult.Success();
    }
}

