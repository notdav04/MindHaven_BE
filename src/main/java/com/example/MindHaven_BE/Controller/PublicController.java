package com.example.MindHaven_BE.Controller;

import com.example.MindHaven_BE.payload.DiarioDTO;
import com.example.MindHaven_BE.payload.PostDTO;
import com.example.MindHaven_BE.payload.ProfessionistaDTO;
import com.example.MindHaven_BE.service.DiarioService;
import com.example.MindHaven_BE.service.PostService;
import com.example.MindHaven_BE.service.ProfessionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    ProfessionistaService professionistaService;

    @Autowired
    PostService postService;

    @Autowired
    DiarioService diarioService;

    @GetMapping("/professionisti")
    public List<ProfessionistaDTO> getAllProfessionisti(){
        System.out.println("sono nel get all------------------------------------------------------------");
        List<ProfessionistaDTO> listaProfessionisti = professionistaService.getAll();
        return listaProfessionisti;
    }

    //professionista con id
    @GetMapping("/professionisti/{id}")
    public ProfessionistaDTO getProfessionistaById(@PathVariable long id){
        ProfessionistaDTO dto = professionistaService.getById(id);
        return dto;
    }

    //professionista con username
    @GetMapping("/professionista/{username")
    public ProfessionistaDTO getProfessionistaByUsername(@PathVariable String username){
        ProfessionistaDTO dto = professionistaService.getByUsername(username);
        return dto;
    }

    @GetMapping("/post")
    public List<PostDTO> getAllPost(){
            List<PostDTO> listaDTO = postService.getAll();
            return listaDTO;
    }


    @GetMapping("/diari")
    public List<DiarioDTO> getAllDiari(){
        List<DiarioDTO> listaDTO = diarioService.getAllDiari();
        return listaDTO;
    }





}
