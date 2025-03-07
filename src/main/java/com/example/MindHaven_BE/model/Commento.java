package com.example.MindHaven_BE.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Commento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String testo;

    @ManyToOne
    @JoinColumn(name= "professionista_id")
    private Professionista professionista;

    @ManyToOne
    @JoinColumn(name= "post_id")
    @JsonIgnore
    private Post post;
}
