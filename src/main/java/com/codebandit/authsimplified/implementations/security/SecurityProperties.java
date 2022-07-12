package com.codebandit.authsimplified.implementations.security;

import com.codebandit.authsimplified.enums.Strategy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@ConfigurationProperties
@Setter
@Getter
public class SecurityProperties {

    private String issuer;
    private Strategy strategy;
    private String secretKey;
    private String publicKeyPath;
    private String privateKeyPath;

    @Bean("securityProperties")
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
