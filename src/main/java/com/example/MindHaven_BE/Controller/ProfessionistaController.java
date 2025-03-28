package com.example.MindHaven_BE.Controller;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.MindHaven_BE.configuration.CloudinaryConfig;
import com.example.MindHaven_BE.payload.*;
import com.example.MindHaven_BE.service.AppuntamentoService;
import com.example.MindHaven_BE.service.DiarioService;
import com.example.MindHaven_BE.service.PostService;
import com.example.MindHaven_BE.service.ProfessionistaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professionista")
public class ProfessionistaController {
    @Autowired
    ProfessionistaService professionistaService;
    @Autowired
    PostService postService;
    @Autowired
    AppuntamentoService appuntamentoService;
    @Autowired
    DiarioService diarioService;
    @Autowired
    Cloudinary cloudinaryConfig;

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

    @GetMapping("/diario/approva")
    public List<DiarioDTO> diariInApprovazione (){
        List<DiarioDTO> listaDTO = diarioService.getDiariInApprovazione();
        return listaDTO;
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

    @GetMapping("/me")
    public ProfessionistaDTO getMe(Authentication auth){
        String username = auth.getName();
        ProfessionistaDTO dto = professionistaService.getMe(username);
        return dto;
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable long id, Authentication auth){
        String username = auth.getName();
        String result = professionistaService.deletePostById(username, id);
        if ( result.contains("errore")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/me/modifica")
    public ResponseEntity<?> modMe(@RequestBody @Validated ProfessionistaDTO dto, Authentication auth, BindingResult validation){
        if (validation.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");
            for (ObjectError errore : validation.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }
        String username = auth.getName();
        String result  = professionistaService.modMe(username, dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PatchMapping("/me/avatar")
    public ResponseEntity<?> cambiaAvatarProfessionista(@RequestPart("avatar") MultipartFile avatar, Authentication auth) {

        try{

            Map mappa = cloudinaryConfig.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());
            String urlImage = mappa.get("secure_url").toString();
            String username = auth.getName();
            professionistaService.modificaAvatar(username, urlImage);
            return new ResponseEntity<>("Immagine avatar sostituita", HttpStatus.OK);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }






}
