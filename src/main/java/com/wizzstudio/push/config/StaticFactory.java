package com.wizzstudio.push.config;

import org.springframework.stereotype.Component;

@Component
public class StaticFactory {
    private static WeChatConfig weChatConfig;

    public StaticFactory(WeChatConfig weChatConfig) {
        StaticFactory.weChatConfig = weChatConfig;
    }

    public static WeChatConfig getWeChatConfig() {
        return weChatConfig;
    }

}
