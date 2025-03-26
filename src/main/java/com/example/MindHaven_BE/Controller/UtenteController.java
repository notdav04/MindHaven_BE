package com.example.MindHaven_BE.Controller;


import com.example.MindHaven_BE.exception.UsernameDuplicateException;
import com.example.MindHaven_BE.model.Pagina;
import com.example.MindHaven_BE.model.Utente;
import com.example.MindHaven_BE.payload.PaginaDTO;
import com.example.MindHaven_BE.payload.PostDTO;
import com.example.MindHaven_BE.payload.ProfessionistaDTO;
import com.example.MindHaven_BE.service.ProfessionistaService;
import com.example.MindHaven_BE.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @Autowired
    ProfessionistaService professionistaService;



    @PostMapping("/diario/pagina")
    public ResponseEntity<?> addPagina(@RequestBody @Validated PaginaDTO dto, Authentication auth, BindingResult validazione){
        if (validazione.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");
            for (ObjectError errore : validazione.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        String username = auth.getName();
        utenteService.newPaginaDiario(dto, username);
        return new ResponseEntity<>("pagina creata con successo", HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getprofilo(Authentication auth){
        System.out.println("-----------------------------------------------------------------------------------"+auth);
        String username = auth.getName();
        Utente utente = utenteService.getMe(username);
        return new ResponseEntity<>(utente, HttpStatus.CREATED);
    }

    @PutMapping("/diario/public")
    public ResponseEntity<?> cambiaStatoDiario( Authentication auth){
        String username = auth.getName();
        String message = utenteService.cambiaStato(username);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }










}
