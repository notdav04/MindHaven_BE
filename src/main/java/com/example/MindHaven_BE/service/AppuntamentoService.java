package com.example.MindHaven_BE.service;

import com.example.MindHaven_BE.model.Appuntamento;
import com.example.MindHaven_BE.model.Professionista;
import com.example.MindHaven_BE.payload.AppuntamentoDTO;
import com.example.MindHaven_BE.repository.AppuntamentoDAORepository;
import com.example.MindHaven_BE.repository.ProfessionistaDAORepository;
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


    //crea appuntamento
    public String newAppuntamento(AppuntamentoDTO dto, String username){
        Professionista professionista = professionistaRepo.findByUsername(username).orElseThrow(()->new RuntimeException("nessun profesisonista trovato con l username fornito!"));
        Appuntamento appuntamento = dto_appuntamento(dto);
        appuntamentoRepo.save(appuntamento);
        professionista.getAppuntamenti().add(appuntamento);
        return "appuntamento con id: " + appuntamento.getId() + " creato dal professionista con id: "+ professionista.getId();
    }


    //travaso da dto a appuntamento
    public Appuntamento dto_appuntamento(AppuntamentoDTO dto){
        Appuntamento appuntamento = new Appuntamento();
        appuntamento.setData(dto.getData());
        return  appuntamento;
    }


}
