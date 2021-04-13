package com.wizzstudio.push.service;

import com.wizzstudio.push.config.FileConfig;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;

import java.lang.constant.Constable;

public class WechatService {
    static WxCpServiceImpl wxCpService;

    static {
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(FileConfig.getCorpId());
        config.setCorpSecret(FileConfig.getCorpSecret());
        config.setAgentId(FileConfig.getAgentId());
        config.setAesKey(FileConfig.getAesKey());
        config.setToken(FileConfig.getToken());

        wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
    }

    public static WxCpServiceImpl getWxCpService() {
        return wxCpService;
    }
}
