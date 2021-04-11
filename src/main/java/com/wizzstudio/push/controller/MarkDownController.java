package com.wizzstudio.push.controller;

import com.wizzstudio.push.config.FileConfig;
import com.wizzstudio.push.model.CommonResult;
import com.wizzstudio.push.service.WechatService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class MarkDownController {
    @RequestMapping("/push/markdown/v1")
    public CommonResult get(HttpServletRequest request, @RequestParam(required = true) String name, @RequestParam(required = false) String text) throws WxErrorException, IOException {

        String content;

        String bodyText = new String(request.getInputStream().readAllBytes());
        if (bodyText.length() > 0) {
            content = bodyText;
        } else if (text != null && text.length() > 0) {
            content = text;
        } else {
            return new CommonResult(0, "id not found", "");

        }

        WxCpMessage message = WxCpMessage.MARKDOWN().agentId(FileConfig.getAgentId()).toUser(name).content(content).build();
        WechatService.getWxCpService().getMessageService().send(message);

        return new CommonResult(0, "success", "");
    }
}
