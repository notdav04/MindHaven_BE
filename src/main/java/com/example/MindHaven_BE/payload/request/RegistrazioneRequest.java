package com.example.MindHaven_BE.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrazioneRequest {

    @NotBlank(message = "il campo username è obbligatorio!")
    private String username;
    @NotBlank(message = "il campo password è obbligatorio!")
    private String password;

    private String ruolo;







}
