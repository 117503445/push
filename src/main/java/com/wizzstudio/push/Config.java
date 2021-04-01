package com.wizzstudio.push;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private static String corpId;
    private static String corpSecret;
    private static Integer agentId;

    public static String getCorpId() {
        return corpId;
    }
    @Value("${wechat.corpId}")
    public void setCorpId(String corpid) {
        Config.corpId = corpid;
    }

    public static String getCorpSecret() {
        return corpSecret;
    }
    @Value("${wechat.corpSecret}")
    public void setCorpSecret(String corpSecret) {
        Config.corpSecret = corpSecret;
    }

    public static Integer getAgentId() {
        return agentId;
    }
    @Value("${wechat.agentId}")
    public static void setAgentId(Integer agentId) {
        Config.agentId = agentId;
    }

}