package com.example.MindHaven_BE.service;

import com.example.MindHaven_BE.model.Commento;
import com.example.MindHaven_BE.model.Post;
import com.example.MindHaven_BE.model.Professionista;
import com.example.MindHaven_BE.payload.CommentoDTO;
import com.example.MindHaven_BE.payload.PostDTO;
import com.example.MindHaven_BE.repository.PostDAORepository;
import com.example.MindHaven_BE.repository.ProfessionistaDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    @Autowired
    ProfessionistaDAORepository professionistaRepo;

    @Autowired
    PostDAORepository postRepo;


    //creazione nuovo post
    public String newPost(PostDTO dto, String username){
        //creo post
        Post post = new Post();
        //recupero professionista
        Professionista professionista = professionistaRepo.findByUsername(username).orElseThrow(()-> new RuntimeException("Professionista non trovato con l username fornito!"));
        //trasformo il dto in post
        post = dto_post(dto);
        //setto il professionista legato al post
        post.setProfessionista(professionista);
        //salvo nel db il post
        postRepo.save(post);
        //return
        return "nuovo post con id: " + post.getId() + " dal professionista con id: " + professionista.getId();
    }

    public List<PostDTO> getAll(){
        List<Post> listaPost = postRepo.findAll();
        List<PostDTO> listaPostDTO = new ArrayList<>();
        listaPost.forEach(ele->{
            listaPostDTO.add(post_dto(ele));
        });
        return listaPostDTO;
    }


    //travaso da Post a PostDTO
    public PostDTO post_dto(Post post){
        PostDTO dto = new PostDTO();
        dto.setTitolo(post.getTitolo());
        dto.setDescrizione(post.getDescrizione());
        dto.setData(post.getData());
        dto.setUsernameProfessionista(post.getProfessionista().getUsername());
        if (post.getCommenti() != null) {
            List<CommentoDTO> commentoDTOList = post.getCommenti().stream()
                    .map(commento -> commento_dto(commento))
                    .collect(Collectors.toList());
            dto.setCommenti(commentoDTOList);
        } else {
            dto.setCommenti(new ArrayList<>());
        }
        return dto;
    }

    //travaso da PostDTO a Post
    public Post dto_post(PostDTO dto){
        Post post = new Post();
        post.setTitolo(dto.getTitolo());
        post.setDescrizione(dto.getDescrizione());
        return post;
    }

    //travaso da commento a dto
    public CommentoDTO commento_dto(Commento commento){
        CommentoDTO dto = new CommentoDTO();
        dto.setTesto(commento.getTesto());
        dto.setUsernameProfessionista(commento.getProfessionista().getUsername());
        return dto;
    }
}
