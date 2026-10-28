package io.github.patrickbg.planeja.infra.security;

import io.github.patrickbg.planeja.dominio.usuario.model.UsuarioEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class JwtService {

    private static final String ALGORITMO = "HmacSHA256";

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-seconds}")
    private long expirationSeconds;

    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    public String generateToken(UsuarioEntity usuario) {
        long expiration = Instant.now().plusSeconds(expirationSeconds).getEpochSecond();
        String header = base64Url("""
                { "alg": "HS256, "typ": "JWT" }
                """);

        String payload = base64Url("""
                        { "sub": "%s", "nome": "%s", "exp": "%d"
                
                """.formatted(usuario.getId().toString(), usuario.getNome(), expiration));

        String signature = assinar(header + "." + payload);

        return header + "." + payload + "." + signature;
    }

    private String assinar(String header) {
        try {
            Mac mac = Mac.getInstance(ALGORITMO);
            byte[] key = secret.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec privateKey = new SecretKeySpec(key, ALGORITMO);
            mac.init(privateKey);
            var publickey = mac.doFinal(header.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(publickey);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao gerar o JWT.", e);
        }
    }

    private String base64Url(String valor) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(valor.getBytes(StandardCharsets.UTF_8));
    }


}
