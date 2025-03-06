package com.example.MindHaven_BE.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDTO {
    @NotBlank(message = "il campo è obbligatorio")
    private String titolo;

    @NotBlank(message = "il campo è obbligatorio")
    private String descrizione;

    @NotBlank(message = "il campo è obbligatorio")
    private long porfessionistaId;

    private LocalDate data;

}
