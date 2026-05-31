package br.com.koldex.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class KeyProvider {

    public PrivateKey loadPrivateKey(String path) {
        try {

            String key = Files.readString(
                    new ClassPathResource(path)
                            .getFile()
                            .toPath()
            );

            key = key
                    .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded =
                    Base64.getDecoder().decode(key);

            PKCS8EncodedKeySpec spec =
                    new PKCS8EncodedKeySpec(decoded);

            return KeyFactory
                    .getInstance("RSA")
                    .generatePrivate(spec);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao carregar private key",
                    e
            );
        }
    }

    public PublicKey loadPublicKey(String path) {
        try {

            String key = Files.readString(
                    new ClassPathResource(path)
                            .getFile()
                            .toPath()
            );

            key = key
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded =
                    Base64.getDecoder().decode(key);

            X509EncodedKeySpec spec =
                    new X509EncodedKeySpec(decoded);

            return KeyFactory
                    .getInstance("RSA")
                    .generatePublic(spec);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao carregar public key",
                    e
            );
        }
    }
}