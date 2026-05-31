package br.com.koldex.auth.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 3, max = 50, message = "Senha inválida")
    private String password;

    @NotBlank(message = "Contexto é obrigatório")
    @Size(min = 2, max = 50, message = "Contexto inválido")
    @Pattern(regexp = "^[A-Z_-]+$", message = "Contexto inválido")
    private String context;
}