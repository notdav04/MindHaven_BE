package com.example.MindHaven_BE.model;

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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;


    @ManyToOne
    @JoinColumn(name = "professionista_id")
    private Professionista professionista;

    @OneToMany(mappedBy = "id")
    private List<Commento> commenti = new ArrayList<>();


}
