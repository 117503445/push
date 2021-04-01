package com.wizzstudio.push;

import com.google.common.base.Strings;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class Controller {
    @RequestMapping("/push")

    public String get(HttpServletRequest request, @RequestParam(required = true) String name, @RequestParam(required = false) String text) throws WxErrorException, IOException {

        String content;

        String bodyText = new String(request.getInputStream().readAllBytes());
        if (bodyText.length() > 0) {
            content = bodyText;
        } else if (text != null && text.length() > 0) {
            content = text;
        } else {
            return "Don't pass text";
        }


        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(Config.getCorpId());
        config.setCorpSecret(Config.getCorpSecret());
        config.setAgentId(Config.getAgentId());

        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
        WxCpMessage message = WxCpMessage.TEXT().agentId(Config.getAgentId()).toUser(name).content(content).build();
        wxCpService.getMessageService().send(message);

        return "hello";
    }
}
