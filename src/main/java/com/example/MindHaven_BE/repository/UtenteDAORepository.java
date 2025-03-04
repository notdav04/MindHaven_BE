package com.example.MindHaven_BE.repository;

import com.example.MindHaven_BE.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteDAORepository extends JpaRepository<Utente, Long> {

    // check login
    public boolean existsByUsernameAndPassword(String username, String password);

    // check duplicate key
    public boolean existsByUsername(String username);
    public Optional<Utente> findByUsername(String username);
}
