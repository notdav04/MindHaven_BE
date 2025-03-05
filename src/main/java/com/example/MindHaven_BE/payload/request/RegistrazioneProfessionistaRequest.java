package com.example.MindHaven_BE.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrazioneProfessionistaRequest {
    @NotBlank(message = "il campo username è obbligatorio!")
    private String username;
    @NotBlank(message = "il campo password è obbligatorio!")
    private String password;
    @NotBlank(message = "il campo nome è obbligatorio!")
    private String nome;
    @NotBlank(message = "il campo cognome è obbligatorio!")
    private String cognome;
    @NotNull(message = "il campo email è obbligatorio!")
    @Email
    private String email;

    @NotBlank(message = "il campo ruolo è obbligatorio")
    private String ruolo;

    private String avatar;

}
