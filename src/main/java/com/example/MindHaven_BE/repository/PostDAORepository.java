package com.example.MindHaven_BE.repository;

import com.example.MindHaven_BE.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDAORepository  extends JpaRepository<Post, Long> {
}
