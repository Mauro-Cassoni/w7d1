package it.epicode.w7d1.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DipendenteRequest {

    @NotBlank(message = "nome obbligatorio")
    private String nome;

    @NotBlank(message = "cognome obbligatorio")
    private String cognome;

    @NotBlank(message = "username obbligatorio")
    private String username;

    @NotBlank(message = "password obbligatoria")
    private String password;

    @NotEmpty(message = "mail obbligatoria")
    @Email
    private String email;

}
