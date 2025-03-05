package com.example.MindHaven_BE.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Diario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean isPublic = false;
    @OneToOne
    @JoinColumn(name = "utente_id")
    @JsonIgnore
    private Utente utente;

    @OneToMany(mappedBy = "diario")
    private List<Pagina> pagine;






    public void addPagina(Pagina pagina){
        pagine.add(pagina);
    }
}
