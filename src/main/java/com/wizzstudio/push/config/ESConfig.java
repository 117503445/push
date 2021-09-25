package com.wizzstudio.push.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "es")
@Setter
@Getter
public class ESConfig {
    private Boolean enabled;
    private String scheme;
    private String host;
    private Integer port;
    private String username;
    private String password;
}