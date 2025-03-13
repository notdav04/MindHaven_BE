package com.example.MindHaven_BE.payload;

import com.example.MindHaven_BE.model.Commento;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostDTO {
    @NotBlank(message = "il campo è obbligatorio")
    private String titolo;

    @NotBlank(message = "il campo è obbligatorio")
    private String descrizione;


    private String usernameProfessionista;

    private LocalDate data;

    private List<CommentoDTO> commenti;

}
