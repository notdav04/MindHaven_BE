package com.example.MindHaven_BE.payload;

import com.example.MindHaven_BE.model.Pagina;
import com.example.MindHaven_BE.model.Utente;
import lombok.Data;

import java.util.List;

@Data
public class DiarioDTO {
    private long id;

    private List<Pagina> pagine;

    private Utente utente ;
}
