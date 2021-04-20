package com.wizzstudio.push.config;

import org.springframework.stereotype.Component;

@Component
public class StaticFactory {
    private static WeChatConfig weChatConfig;
    private static ESConfig esConfig;

    public StaticFactory(WeChatConfig weChatConfig, ESConfig esConfig) {
        StaticFactory.weChatConfig = weChatConfig;
        StaticFactory.esConfig = esConfig;
    }

    public static WeChatConfig getWeChatConfig() {
        return weChatConfig;
    }

    public static ESConfig getEsConfig() {
        return esConfig;
    }
}
