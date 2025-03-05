package com.example.MindHaven_BE.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username è un campo obbligatorio")
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank(message = "Password è un campo obbligatorio")
    @Size(min = 3, max = 20)
    private String password;


}
