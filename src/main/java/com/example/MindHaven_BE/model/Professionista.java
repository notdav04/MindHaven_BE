package com.example.MindHaven_BE.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name= "professionisti")
public class Professionista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;


    @Column(nullable = false)
    private String ruolo;

    @Column(nullable = false)
    @JsonIgnore
    private String nome;

    @Column(nullable = false)
    @JsonIgnore
    private String cognome;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)

    @OneToMany(mappedBy = "professionista")
    @JsonIgnore
    private List<Post> posts;

    private List<Appuntamento> appuntamenti = new ArrayList<>();






}
