package io.github.patrickbg.planeja.dominio.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroForm(
        @NotBlank(message = "Informe o nome do usuário")
        String nome,
        @NotBlank(message = "Informe o login do usuário")
        String login,
        @NotBlank(message = "Informe a senha do usuário")
        @Size(min = 8, message = "A senha deve ter pelo menos 6 caracteres.")
        String senha
) {
}
