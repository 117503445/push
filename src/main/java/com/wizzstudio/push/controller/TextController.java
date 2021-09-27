package com.wizzstudio.push.controller;

import com.wizzstudio.push.config.ApiVersion;
import com.wizzstudio.push.config.ESConfig;
import com.wizzstudio.push.config.StaticFactory;
import com.wizzstudio.push.model.CommonResult;
import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("push/text")
public class TextController {

    @Autowired
    ESConfig esConfig;

    @RequestMapping("/{version}")
    @ApiVersion
    public CommonResult pushText(HttpServletRequest request, @RequestParam() String name, @RequestParam(required = false) String text) throws WxErrorException, IOException {
        String content;
        String bodyText = new String(request.getInputStream().readAllBytes());
        if (bodyText.length() > 0) {
            content = bodyText;
        } else if (text != null && text.length() > 0) {
            content = text;
        } else {
            return new CommonResult(2, "content not found", null);
        }
        WxCpMessage message = WxCpMessage.TEXT().agentId(StaticFactory.getWeChatConfig().getAgentId()).toUser(name).content(content).build();
        WechatService.getWxCpService().getMessageService().send(message);
        return CommonResult.ok();
    }


    @RequestMapping("/{version}")
    @ApiVersion(2)
    public CommonResult pushTextWithNickname(HttpServletRequest request, @RequestParam() String name, @RequestParam(required = false) String text) throws IOException, WxErrorException {
        String content;
        String bodyText = new String(request.getInputStream().readAllBytes());
        if (bodyText.length() > 0) {
            content = bodyText;
        } else if (text != null && text.length() > 0) {
            content = text;
        } else {
            return new CommonResult(2, "content not found", null);
        }
        WxCpMessage message = WxCpMessage.TEXT().agentId(StaticFactory.getWeChatConfig().getAgentId()).toUser(name).content(content).build();
        WechatService.getWxCpService().getMessageService().send(message);
        return CommonResult.ok();
    }

}
