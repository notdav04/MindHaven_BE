package com.example.MindHaven_BE.security.services;

import com.example.MindHaven_BE.model.Professionista;
import com.example.MindHaven_BE.model.Utente;
import com.example.MindHaven_BE.repository.ProfessionistaDAORepository;
import com.example.MindHaven_BE.repository.UtenteDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UtenteDAORepository repo;

    @Autowired
    ProfessionistaDAORepository repoProfessionista;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Professionista> professionista = repoProfessionista.findByUsername(username);
        Optional<Utente> utente = repo.findByUsername(username);


        if (professionista.isPresent()){
            Professionista objFound = professionista.get();
            UserDetails dettagliUtente= User.builder().username(objFound.getUsername())
                    .password((objFound.getPassword()))
                    .roles(String.valueOf(objFound.getRuolo())).build();

            return dettagliUtente;
        } else if (utente.isPresent()) {
            Utente objFound = utente.get();
            UserDetails dettagliUtente= User.builder().username(objFound.getUsername())
                    .password((objFound.getPassword()))
                    .roles(String.valueOf(objFound.getRuolo())).build();

            return dettagliUtente;
        }else{
            throw new UsernameNotFoundException("username non trovato");
        }



    }
}
