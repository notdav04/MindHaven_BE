package com.example.MindHaven_BE.Controller;

import com.example.MindHaven_BE.payload.AppuntamentoDTO;
import com.example.MindHaven_BE.payload.CommentoDTO;
import com.example.MindHaven_BE.payload.PostDTO;
import com.example.MindHaven_BE.payload.ProfessionistaDTO;
import com.example.MindHaven_BE.service.AppuntamentoService;
import com.example.MindHaven_BE.service.PostService;
import com.example.MindHaven_BE.service.ProfessionistaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;

@RestController
@RequestMapping("/professionista")
public class ProfessionistaController {
    @Autowired
    ProfessionistaService professionistaService;
    @Autowired
    PostService postService;
    @Autowired
    AppuntamentoService appuntamentoService;

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
            String message = postService.newPost(dto, username);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    //endpoint per aggiungere commenti sul post
    @PostMapping("/post/{id}/commento")
    public ResponseEntity<?> creaCommento(@RequestBody @Validated CommentoDTO dto, @PathVariable long id,Authentication auth, BindingResult validation){
        if (validation.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");
            for (ObjectError errore : validation.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        String username = auth.getName();
        String message = professionistaService.newCommento(dto, id, username);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    @PostMapping("/appuntamento/new")
    public ResponseEntity<?> creaAppuntamento(@RequestBody @Validated AppuntamentoDTO dto, Authentication auth, BindingResult validation){
        if (validation.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");
            for (ObjectError errore : validation.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        String username = auth.getName();
        String message = appuntamentoService.newAppuntamento(dto, username);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/diario/approva/{id}")
    public ResponseEntity<?> approvaDiario (@PathVariable long id , Authentication auth){
        HttpStatus status = professionistaService.approvaDiario(id);
        if (status==HttpStatus.OK){
            return new ResponseEntity<>("approvazione del diario effettuata correttamente!", status);
        } else {
            return new ResponseEntity<>("il diario non è in fase di pubblicazione!", status);
        }

    }





}
