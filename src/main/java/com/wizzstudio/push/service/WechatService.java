package com.wizzstudio.push.service;

import com.wizzstudio.push.Config;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;

public class WechatService {
    static WxCpServiceImpl wxCpService;

    static {
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(Config.getCorpId());
        config.setCorpSecret(Config.getCorpSecret());
        config.setAgentId(Config.getAgentId());

        wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
    }

    public static WxCpServiceImpl getWxCpService() {
        return wxCpService;
    }
}
