package br.com.koldex.auth.api.controller;

import br.com.koldex.auth.security.JwtKeyLoader;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class JwksController {

    private final JwtKeyLoader jwtKeyLoader;

    @GetMapping("/jwks")
    public Object jwks() {

        RSAKey rsaKey = new RSAKey.Builder(
                jwtKeyLoader.getPublicKey()
        )
                .keyID("main-key")
                .build();

        return new JWKSet(rsaKey)
                .toJSONObject();
    }
}