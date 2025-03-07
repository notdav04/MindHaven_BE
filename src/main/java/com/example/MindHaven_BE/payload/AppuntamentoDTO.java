package com.example.MindHaven_BE.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppuntamentoDTO {
    @NotNull(message = "il campo data è obbligatorio!")
    private LocalDate data;

    private boolean disponibile;
}
