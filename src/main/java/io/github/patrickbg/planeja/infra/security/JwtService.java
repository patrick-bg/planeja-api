package io.github.patrickbg.planeja.infra.security;

import io.github.patrickbg.planeja.dominio.usuario.UsuarioRepository;
import io.github.patrickbg.planeja.dominio.usuario.model.UsuarioEntity;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
public class JwtService {

    private static final String ALGORITMO = "HmacSHA256";

    @Value("${app.jwt.secret}")
    private String secret;

    @Getter
    @Value("${app.jwt.expiration-seconds}")
    private long expirationSeconds;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String gerarToken(UsuarioEntity usuario) {
        long expiration = Instant.now().plusSeconds(expirationSeconds).getEpochSecond();
        String header = base64Url("""
                { "alg": "HS256", "typ": "JWT" }
                """);

        String payload = base64Url("""
                        { "sub": "%s", "nome": "%s", "exp": "%d"}
                
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
            byte[] publicKey = mac.doFinal(header.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao gerar o JWT.", e);
        }
    }

    private String base64Url(String valor) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(valor.getBytes(StandardCharsets.UTF_8));
    }


    public boolean isTokenValido(String token) {

        try {
            String[] partesDoToken = token.split("\\.");
            String header = partesDoToken[0];
            String payload = partesDoToken[1];
            String signature = partesDoToken[2];

            String assinaturaEsperada = assinar(header + "." +  payload);
            if(!assinaturaEsperada.equals(signature)) {
                return false;
            }

            var payloadDecodificado = Base64.getUrlDecoder().decode(payload);
            var payloadString = new String(payloadDecodificado, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payloadString);
            long expiraEm = jsonNode.get("exp").asLong();

            long miliSegundosDataHoraAtual = Instant.now().getEpochSecond();

            return miliSegundosDataHoraAtual < expiraEm;

        }catch (RuntimeException e) {
            return false;
        }

    }

    public UsuarioEntity getUsuario(String token) {
        String[] partesToken = token.split("\\.");
        byte[] payloadDecodificado = Base64.getUrlDecoder().decode(partesToken[1]);
        String payload = new String(payloadDecodificado, StandardCharsets.UTF_8);
    
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);

        String id = jsonNode.get("sub").asString();

        return usuarioRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Erro ao tentar resolver usuário."));
    }
}
