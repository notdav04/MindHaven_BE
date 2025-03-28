package com.example.MindHaven_BE.service;

import com.example.MindHaven_BE.model.Diario;
import com.example.MindHaven_BE.model.Pagina;
import com.example.MindHaven_BE.payload.DiarioDTO;
import com.example.MindHaven_BE.repository.DiarioDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DiarioService {

    @Autowired
    DiarioDAORepository diarioRepo;

    public List<DiarioDTO> getAllDiari(){
        List<Diario> lista = diarioRepo.findByIsPublicTrue();
        List<DiarioDTO> listaDTO = new ArrayList<>();
        lista.forEach(ele->listaDTO.add(diario_dto(ele)));
        return listaDTO;
    }

    public List<DiarioDTO> getDiariInApprovazione(){
        List<Diario> lista = diarioRepo.findByRequestedPublicTrue();
        List<DiarioDTO> listaDTO = new ArrayList<>();
        lista.forEach(ele->listaDTO.add(diario_dto(ele)));
        return listaDTO;
    }


    //travaso da Diario a DiarioDTO
    public DiarioDTO diario_dto(Diario diario){
        DiarioDTO dto = new DiarioDTO();

        List<Pagina> listaPagine = new ArrayList<>();
        diario.getPagine().forEach(ele->listaPagine.add(ele));
        dto.setId(diario.getId());
        dto.setPagine(listaPagine);
        dto.setUtente(diario.getUtente());
        return dto;
    }
}
