package com.example.MindHaven_BE.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Appuntamento {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDate data;

    private boolean disponibile = true;
}
