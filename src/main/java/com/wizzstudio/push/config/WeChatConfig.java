package com.wizzstudio.push.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
@Setter
@Getter
public class WeChatConfig {
    private String corpId;
    private String corpSecret;
    private Integer agentId;
    private String aesKey;
    private String token;
}