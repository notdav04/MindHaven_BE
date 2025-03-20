package com.example.MindHaven_BE.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

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
    @Column(nullable= false, columnDefinition = "TEXT")
    private String contenuto;
    @ManyToOne
    @JoinColumn(name= "diario_id")
    @JsonIgnore
    private Diario diario;

}
