package com.wizzstudio.push.controller;

import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/24/20:44
 * @Description: 消息接收并处理的Controller
 */

@RestController
public class MessageController {

    @Autowired
    WxCpMessageRouter router;

    /**
     * 企业微信官方验证回调是否正确时调用的Api
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echoStr
     * @return
     */
    @GetMapping("/message")
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

    /**
     * 用户主动发消息和用户触发事件时请求的接口,接收后进行路由到不同的Handler进行处理,并根据企业微信的规定返回格式进行密文返回
     * @param requestBody
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    @PostMapping("/message")
    public String receive(@RequestBody String requestBody,
                                @RequestParam(name = "msg_signature") String signature,
                                @RequestParam(name = "timestamp") String timestamp,
                                @RequestParam(name = "nonce") String nonce){
        System.out.println("消息请求过来了");
        //解析获取请求体的明文
        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(requestBody, WechatService.getWxCpService().getWxCpConfigStorage(),
                timestamp, nonce, signature);
        System.out.println("准备路由");
        //进行消息的路由
        WxCpXmlOutMessage outMessage = router.route(inMessage);
        System.out.println("路由完成");
        //进行消息的加密后推送给用户
        String encryptedXml = outMessage.toEncryptedXml(WechatService.getWxCpService().getWxCpConfigStorage());
        System.out.println("返回给用户");
        return encryptedXml;
    }



}
