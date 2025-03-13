package com.example.MindHaven_BE.service;

import com.example.MindHaven_BE.model.Appuntamento;
import com.example.MindHaven_BE.model.Professionista;
import com.example.MindHaven_BE.model.Utente;
import com.example.MindHaven_BE.payload.AppuntamentoDTO;
import com.example.MindHaven_BE.repository.AppuntamentoDAORepository;
import com.example.MindHaven_BE.repository.ProfessionistaDAORepository;
import com.example.MindHaven_BE.repository.UtenteDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppuntamentoService {
    @Autowired
    AppuntamentoDAORepository appuntamentoRepo;

    @Autowired
    ProfessionistaDAORepository professionistaRepo;

    @Autowired
    UtenteDAORepository utenteRepo;


    //crea appuntamento
    public String newAppuntamento(AppuntamentoDTO dto, String username){
        Professionista professionista = professionistaRepo.findByUsername(username).orElseThrow(()->new RuntimeException("nessun profesisonista trovato con l username fornito!"));
        Appuntamento appuntamento = dto_appuntamento(dto);
        appuntamentoRepo.save(appuntamento);
        professionista.getAppuntamenti().add(appuntamento);
        return "appuntamento con id: " + appuntamento.getId() + " creato dal professionista con id: "+ professionista.getId();
    }

//    //prenota appuntamento
//    public String prenotaAppuntamento(long idAppuntamento, String username){
//        Appuntamento appuntamento = appuntamentoRepo.findById(idAppuntamento).orElseThrow(()-> new RuntimeException("nessun appuntamento trovato con l id fornito!"));
//        Utente utente = utenteRepo.findByUsername(username).orElseThrow(()->new RuntimeException("nessun utente trovato con l username fornito!"));
//        appuntamento.setDisponibile(false);
//        appuntamento.setUtente(utente);
//        return "appuntamento prenotato con successo!";
//    }


    //travaso da dto a appuntamento
    public Appuntamento dto_appuntamento(AppuntamentoDTO dto){
        Appuntamento appuntamento = new Appuntamento();
        appuntamento.setData(dto.getData());
        return  appuntamento;
    }




}
