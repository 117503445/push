package com.wizzstudio.push.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {

    private static String corpId;
    private static String corpSecret;
    private static Integer agentId;
    private static String aesKey;
    private static String token;


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

    public static String getAesKey() {
        return aesKey;
    }

    @Value("${wechat.aesKey}")
    public void setAesKey(String aesKey) {
        FileConfig.aesKey = aesKey;
    }

    public static String getToken() {
        return token;
    }

    @Value("${wechat.token}")
    public void setToken(String token) {
        FileConfig.token = token;
    }
}