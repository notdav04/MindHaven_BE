package com.example.MindHaven_BE.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;


    @Column(nullable = false)
    @JsonIgnore
    private String ruolo;
    @OneToOne
    @JoinColumn(name="diario_id")
    @JsonIgnore
    private Diario diario = new Diario();





}
