package com.example.MindHaven_BE.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pagina {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDate data = LocalDate.now();
    @Column(nullable= false)
    private String contenuto;
    @ManyToOne
    @JoinColumn(name= "diario_id")
    private Diario diario;

}
