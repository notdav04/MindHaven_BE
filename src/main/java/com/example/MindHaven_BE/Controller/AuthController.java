package com.example.MindHaven_BE.Controller;


import com.example.MindHaven_BE.exception.UsernameDuplicateException;
import com.example.MindHaven_BE.model.Utente;
import com.example.MindHaven_BE.payload.request.LoginRequest;
import com.example.MindHaven_BE.payload.request.RegistrazioneProfessionistaRequest;
import com.example.MindHaven_BE.payload.request.RegistrazioneRequest;
import com.example.MindHaven_BE.payload.response.LoginResponse;
import com.example.MindHaven_BE.service.ProfessionistaService;
import com.example.MindHaven_BE.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Binding;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UtenteService utenteService;

    @Autowired
    ProfessionistaService professionistaService;


    @PostMapping("/new/utente")
    public ResponseEntity<?> newUtente(@RequestBody @Validated RegistrazioneRequest body, BindingResult validazione){
        if (validazione.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");

            for (ObjectError errore : validazione.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        try {
            String messaggio = utenteService.newUser(body);
            return new ResponseEntity<>(messaggio, HttpStatus.OK);
        } catch (UsernameDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/new/professionista")
    public ResponseEntity<?> newProfessionista(@RequestBody @Validated RegistrazioneProfessionistaRequest body, BindingResult validazione){
        if (validazione.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");

            for (ObjectError errore : validazione.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        try {
            String messaggio = professionistaService.newProfessionista(body);

            return new ResponseEntity<>(messaggio, HttpStatus.OK);
        } catch (UsernameDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login/utente")
    public ResponseEntity<?> loginUtente(@Validated @RequestBody LoginRequest loginDTO, BindingResult checkValidazione) {
        try {
            if (checkValidazione.hasErrors()) {
                StringBuilder erroriValidazione = new StringBuilder("Problemi nella validazione\n");
                for (ObjectError errore : checkValidazione.getAllErrors()) {
                    erroriValidazione.append(errore.getDefaultMessage());
                }
                return new ResponseEntity<>(erroriValidazione.toString(), HttpStatus.BAD_REQUEST);
            }
            LoginResponse response = utenteService.login(loginDTO.getUsername(), loginDTO.getPassword());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login/professionista")
    public ResponseEntity<?> loginProfessionista(@Validated @RequestBody LoginRequest loginDTO, BindingResult checkValidazione) {
        try {
            if (checkValidazione.hasErrors()) {
                StringBuilder erroriValidazione = new StringBuilder("Problemi nella validazione\n");
                for (ObjectError errore : checkValidazione.getAllErrors()) {
                    erroriValidazione.append(errore.getDefaultMessage());
                }
                return new ResponseEntity<>(erroriValidazione.toString(), HttpStatus.BAD_REQUEST);
            }
            LoginResponse response = professionistaService.login(loginDTO.getUsername(), loginDTO.getPassword());
            System.out.println(loginDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Credenziali non valide", HttpStatus.BAD_REQUEST);
        }
    }
}
