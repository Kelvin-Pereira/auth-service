package br.com.koldex.auth.api.domain.service;


import br.com.koldex.auth.api.domain.entity.UserAccount;
import br.com.koldex.auth.security.JwtProperties;
import br.com.koldex.auth.security.JwtKeyLoader;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtKeyLoader jwtKeyLoader;
    private final JwtProperties properties;

    public String generateAccessToken(
            UserAccount user,
            String context,
            List<String> roles,
            List<String> actions
    ) {

        Instant now = Instant.now();

        Instant expiration = now.plus(
                properties.getAccessTokenExpiration(),
                ChronoUnit.MINUTES
        );

        return Jwts.builder()
                .subject(user.getId())
                .issuer(properties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim("email", user.getEmail())
                .claim("context", context)
                .claim("roles", roles)
                .claim("actions", actions)
                .signWith(jwtKeyLoader.getPrivateKey())
                .compact();
    }

}