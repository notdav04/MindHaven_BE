package com.example.MindHaven_BE.service;

import com.example.MindHaven_BE.exception.EmailDuplicateException;
import com.example.MindHaven_BE.exception.UsernameDuplicateException;
import com.example.MindHaven_BE.model.Professionista;
import com.example.MindHaven_BE.model.Utente;
import com.example.MindHaven_BE.payload.request.RegistrazioneProfessionistaRequest;
import com.example.MindHaven_BE.payload.request.RegistrazioneRequest;
import com.example.MindHaven_BE.payload.response.LoginResponse;
import com.example.MindHaven_BE.repository.ProfessionistaDAORepository;
import com.example.MindHaven_BE.security.services.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    //registrazione professionista
    public String newProfessionista(RegistrazioneProfessionistaRequest registrazione){
        //creazione utente e set delle proprieta
        String passwordCodificata = encoder.encode(registrazione.getPassword());
        checkDuplicateKey(registrazione.getUsername(), registrazione.getEmail());
        Professionista professionista = registrazione_Professionista(registrazione);
        professionista.setPassword(passwordCodificata);

        //return
        Long id = professionistaRepo.save(professionista).getId();
        System.out.println(professionista);
        return "Nuovo utente registrato con id: " + id;
    }

    //controllo se l username o  la password è gia presente nel db
    public void checkDuplicateKey(String username, String email)throws UsernameDuplicateException, EmailDuplicateException{
        if(professionistaRepo.existsByUsername(username)){
            throw new UsernameDuplicateException("Username gia utilizzato, non disponibile");
        } else if (professionistaRepo.existsByEmail(email)){
            throw new EmailDuplicateException("Email gia utilizzata, non disponibile");
        }
    }

    //login utente
    public LoginResponse login(String username, String password){
        // 1. AUTENTICAZIONE DELL'UTENTE IN FASE DI LOGIN
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // 2. INSERIMENTO DELL'AUTENTICAZIONE UTENTE NEL CONTESTO DELLA SICUREZZA
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //3. RECUPERO RUOLI --> String
        String ruolo=null;
        for(Object role :authentication.getAuthorities()){
            ruolo=role.toString();
            break;
        }
        // 4. GENERO L'UTENTE
        Professionista professionista = new Professionista();
        professionista.setUsername(username);
        professionista.setRuolo(ruolo);
        // 5. GENERO IL TOKEN
        String token = jwtUtil.creaToken(professionista);
        // 6. CREO L'OGGETTO DI RISPOSTA AL CLIENT
        return new LoginResponse(username, token);
    }


    public Professionista registrazione_Professionista(RegistrazioneProfessionistaRequest reg){
        Professionista professionista = new Professionista();
        professionista.setNome(reg.getNome());
        professionista.setCognome(reg.getCognome());
        professionista.setEmail(reg.getEmail());
        professionista.setUsername(reg.getUsername());
        professionista.setRuolo(reg.getRuolo());
        return professionista;

    }


}
