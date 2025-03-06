package com.example.MindHaven_BE.payload;

import lombok.Data;

import java.util.List;

@Data
public class ProfessionistaDTO {

    private String nome;

    private String cognome;

    private String email;

    private String username;

    private List<PostDTO> listapost;
}
