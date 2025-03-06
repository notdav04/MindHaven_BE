package com.example.MindHaven_BE.Controller;

import com.example.MindHaven_BE.payload.PostDTO;
import com.example.MindHaven_BE.payload.ProfessionistaDTO;
import com.example.MindHaven_BE.service.ProfessionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professionista")
public class ProfessionistaController {
    @Autowired
    ProfessionistaService professionistaService;

    //endpoint per creazione di un nuovo post da professionista
    @PostMapping("/post/new")
    public ResponseEntity<?> createPost(@RequestBody @Validated PostDTO dto, Authentication auth, BindingResult validation){
        if (validation.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");
            for (ObjectError errore : validation.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
            String username = auth.getName();
            String message = professionistaService.newPost(dto, username);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
    }




}
