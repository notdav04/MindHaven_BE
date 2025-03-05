package com.example.MindHaven_BE.repository;

import com.example.MindHaven_BE.model.Diario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiarioDAORepository extends JpaRepository<Diario, Long> {
}
