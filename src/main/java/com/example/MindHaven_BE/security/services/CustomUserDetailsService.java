package com.example.MindHaven_BE.security.services;

import com.example.MindHaven_BE.model.Utente;
import com.example.MindHaven_BE.repository.UtenteDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UtenteDAORepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utente user = repo.findByUsername(username).orElseThrow();

        UserDetails dettagliUtente= User.builder().username(user.getUsername())
                .password((user.getPassword()))
                .roles(String.valueOf(user.getRuolo())).build();

        return dettagliUtente;
    }
}
