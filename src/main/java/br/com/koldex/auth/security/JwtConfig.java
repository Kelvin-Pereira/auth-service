package br.com.koldex.auth.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtKeyLoader jwtKeyLoader;

    @Bean
    public JWKSource<SecurityContext> jwkSource() {

        RSAKey rsaKey = new RSAKey.Builder(
                jwtKeyLoader.getPublicKey()
        )
                .privateKey(
                        jwtKeyLoader.getPrivateKey()
                )
                .keyID("main-key")
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);

        return (selector, context) ->
                selector.select(jwkSet);
    }
}
