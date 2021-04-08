package com.wizzstudio.push.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {

    private static String corpId;
    private static String corpSecret;
    private static Integer agentId;

    public static String getCorpId() {
        return corpId;
    }
    @Value("${wechat.corpId}")
    public void setCorpId(String corpid) {
        FileConfig.corpId = corpid;
    }

    public static String getCorpSecret() {
        return corpSecret;
    }
    @Value("${wechat.corpSecret}")
    public void setCorpSecret(String corpSecret) {
        FileConfig.corpSecret = corpSecret;
    }

    public static Integer getAgentId() {
        return agentId;
    }
    @Value("${wechat.agentId}")
    public void setAgentId(Integer agentId) {
        FileConfig.agentId = agentId;
    }

}