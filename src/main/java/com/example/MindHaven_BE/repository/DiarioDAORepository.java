package com.example.MindHaven_BE.repository;

import com.example.MindHaven_BE.model.Diario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiarioDAORepository extends JpaRepository<Diario, Long> {

        public List<Diario> findByIsPublicTrue();
        public List<Diario> findByRequestedPublicTrue();
}
