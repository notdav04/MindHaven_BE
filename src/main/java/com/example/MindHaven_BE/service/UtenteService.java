package com.example.MindHaven_BE.service;


import com.example.MindHaven_BE.exception.UsernameDuplicateException;
import com.example.MindHaven_BE.model.Diario;
import com.example.MindHaven_BE.model.Pagina;
import com.example.MindHaven_BE.model.Professionista;
import com.example.MindHaven_BE.model.Utente;
import com.example.MindHaven_BE.payload.PaginaDTO;
import com.example.MindHaven_BE.payload.ProfessionistaDTO;
import com.example.MindHaven_BE.payload.UserDTO;
import com.example.MindHaven_BE.payload.request.RegistrazioneRequest;
import com.example.MindHaven_BE.payload.response.LoginResponse;
import com.example.MindHaven_BE.repository.DiarioDAORepository;
import com.example.MindHaven_BE.repository.PaginaDAORepository;
import com.example.MindHaven_BE.repository.UtenteDAORepository;
import com.example.MindHaven_BE.security.services.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UtenteService {

    @Autowired
    DiarioDAORepository diarioRepo;


    @Autowired
    UtenteDAORepository userRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PaginaDAORepository paginaRepo;

    //registrazione utente
    public String newUser(RegistrazioneRequest registrazione){
        //creazione utente e set delle proprieta
        String passwordCodificata = encoder.encode(registrazione.getPassword());
        checkDuplicateKey(registrazione.getUsername());
        Utente user = registrazioneRequest_Utente(registrazione);
        user.setPassword(passwordCodificata);
        //controllo per ruolo
        if ( registrazione.getRuolo() == null || registrazione.getRuolo().equals("USER")){
            user.setRuolo("USER");
        } else if(registrazione.getRuolo().equals("ADMIN")){
            user.setRuolo("ADMIN");
        }else{
            throw new RuntimeException("Errore: il valore inserito come ruolo non è valido");
        }
        //salvo le informazioni dell utente nel diario
        user.getDiario().setUtente(user);
        diarioRepo.save(user.getDiario());
        //return
        Long id = userRepo.save(user).getId();
        return "Nuovo utente registrato con id: " + id;
    }

    //controllo se l username è gia presente nel db
    public void checkDuplicateKey(String username) throws UsernameDuplicateException {
        if (userRepo.existsByUsername(username)) {
            throw new UsernameDuplicateException("Username già utilizzato, non disponibile");
        }
    }

    //login utente
    public LoginResponse login(String username, String password){

        // 1. AUTENTICAZIONE DELL'UTENTE IN FASE DI LOGIN
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // 2. INSERIMENTO DELL'AUTENTICAZIONE UTENTE NEL CONTESTO DELLA SICUREZZA
        SecurityContextHolder.getContext().setAuthentication(authentication);

         //3. RECUPERO RUOLI --> String
        String ruolo=null;
        for(Object role :authentication.getAuthorities()){
            ruolo=role.toString();
            break;
        }

//        Erole ruolo = authentication.getAuthorities();
        // 4. GENERO L'UTENTE
        Utente user = new Utente();
        user.setUsername(username);
        user.setRuolo(ruolo);

        // 5. GENERO IL TOKEN
        String token = jwtUtil.creaToken(user);

        // 6. CREO L'OGGETTO DI RISPOSTA AL CLIENT
        return new LoginResponse(username, token);
    }

    //aggiunta di una pagina al diario
    public String newPaginaDiario(PaginaDTO dto, String username){
        //recupero l utente
        Utente utente = userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("nessun utente trovato con l username fornito!"));
        //creo la pagina
        Pagina pagina = new Pagina();
        pagina.setDiario(utente.getDiario());
        pagina.setContenuto(dto.getContenuto());
        //aggiungo la pagina al diario
        utente.getDiario().addPagina(pagina);
        //salvo la pagina nel db e aggiorno il diario
        paginaRepo.save(pagina);
        diarioRepo.save(utente.getDiario());
        //return
        return "pagina aggiunta correttamente al diario";
    }

    //stampa del utente loggato
    public Utente getMe(String username){
        Utente utente = userRepo.findByUsername(username).orElseThrow(()-> new RuntimeException("nessun utente trovato con l username indicato!"));
        return utente;
    }

    //cambia stato del diario
    public String cambiaStato(String username) {
        Utente utente = userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("nessun utente trovato con l username fornito!"));
        Diario diario = utente.getDiario();
        if (diario.isRequestedPublic() || diario.isPublic()){
            diario.setRequestedPublic(false);
            diario.setPublic(false);
        } else if(!diario.isRequestedPublic()){
            diario.setRequestedPublic(true);
        }
        diarioRepo.save(diario);
        return "stato del diario cambiato correttamente!";
    }

    //modifica profilo utente
    public String modMe(String username, UserDTO dto){
        Utente user  = userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("nessun utente trovato con l username indicato"));

        user.setUsername(dto.getUsername());
        userRepo.save(user);
        return "profilo utente aggiornato correttamente ";
    }

    //travaso da registrazioneRequest a Utente
    public Utente registrazioneRequest_Utente(RegistrazioneRequest request) {
        Utente utente = new Utente();
        utente.setUsername(request.getUsername());
        return utente;
    }

}
