package com.example.MindHaven_BE.repository;

import com.example.MindHaven_BE.model.Commento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentoDAORepository  extends JpaRepository<Commento, Long> {
}
