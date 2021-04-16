package com.wizzstudio.push.service;

import com.wizzstudio.push.config.StaticFactory;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;

public class WechatService {
    static WxCpServiceImpl wxCpService;

    static {
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(StaticFactory.getWeChatConfig().getCorpId());
        config.setCorpSecret(StaticFactory.getWeChatConfig().getCorpSecret());
        config.setAgentId(StaticFactory.getWeChatConfig().getAgentId());
        config.setAesKey(StaticFactory.getWeChatConfig().getAesKey());
        config.setToken(StaticFactory.getWeChatConfig().getToken());

        wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
    }

    public static WxCpServiceImpl getWxCpService() {
        return wxCpService;
    }
}
