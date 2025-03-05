package com.example.MindHaven_BE.Controller;


import com.example.MindHaven_BE.model.Pagina;
import com.example.MindHaven_BE.payload.PaginaDTO;
import com.example.MindHaven_BE.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    UtenteService utenteService;



    @PostMapping("/diario/pagina")

    public ResponseEntity<?> addPagina(@RequestBody @Validated PaginaDTO dto, Authentication auth){

        String username = auth.getName();

        utenteService.newPaginaDiario(dto, username);





return new ResponseEntity<>("pagina creata con successo", HttpStatus.CREATED);

    }





}
