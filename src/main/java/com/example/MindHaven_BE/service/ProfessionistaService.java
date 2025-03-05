package com.example.MindHaven_BE.service;

import com.example.MindHaven_BE.repository.ProfessionistaDAORepository;
import com.example.MindHaven_BE.security.services.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfessionistaService {

    @Autowired
    ProfessionistaDAORepository professionistaRepo;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;



}
