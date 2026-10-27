package io.github.patrickbg.planeja.dominio.usuario.dto;

public record AuthResponse(
        String token,
        String tipo,
        long expiraEm,
        String nome
) {
}
