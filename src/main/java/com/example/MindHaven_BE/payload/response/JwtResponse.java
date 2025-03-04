package com.example.MindHaven_BE.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtResponse {

    // Info che ritorneremo all'interno di un oggetto JSON tramite ResponseEntity
    private String username;
    private Long id;
    private String email;
    private List<String> ruoli;
    private String token;
    private String type ="Bearer ";

    public JwtResponse(String username, Long id, String email, List<String> ruoli, String token) {
        this.username = username;
        this.id = id;
        this.email = email;
        this.ruoli = ruoli;
        this.token = token;
    }
}