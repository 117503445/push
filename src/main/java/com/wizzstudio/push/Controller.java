package com.wizzstudio.push;

import com.google.common.base.Strings;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/push")

    public String get(@RequestParam(required = true) String name, @RequestParam(required = false) String text) throws WxErrorException {
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(Config.getCorpId());
        config.setCorpSecret(Config.getCorpSecret());
        config.setAgentId(Config.getAgentId());

        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
        WxCpMessage message = WxCpMessage.TEXT().agentId(1000002).toUser(name).content(text).build();
        wxCpService.getMessageService().send(message);
        return "hello";
    }
}
