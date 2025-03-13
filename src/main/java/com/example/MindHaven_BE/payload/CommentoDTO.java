package com.example.MindHaven_BE.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentoDTO {

    @NotBlank(message = "il campo testo è obbligatorio")
    private String testo;

    private String usernameProfessionista;





}
