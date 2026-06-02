package br.com.koldex.auth.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private String issuer;
    private Resource publicKeyPath;
    private Resource privateKeyPath;
    private Long accessTokenExpiration;
    private Long refreshTokenExpirationDays;

}