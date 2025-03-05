package com.example.MindHaven_BE.repository;

import com.example.MindHaven_BE.model.Professionista;
import com.example.MindHaven_BE.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionistaDAORepository  extends JpaRepository<Professionista, Long> {
    // check login
    public boolean existsByUsernameAndPassword(String username, String password);

    // check duplicate key
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    public Optional<Professionista> findByUsername(String username);


}
