package com.example.MindHaven_BE.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.w3c.dom.Text;

@Data
public class PaginaDTO {


    @NotBlank(message= "il campo contenuto è obbligatorio")
    private String contenuto;
}
