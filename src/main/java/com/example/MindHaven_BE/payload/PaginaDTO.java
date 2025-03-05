package com.example.MindHaven_BE.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaginaDTO {


    @NotBlank(message= "il campo contenuto è obbligatorio")
    private String contenuto;
}
