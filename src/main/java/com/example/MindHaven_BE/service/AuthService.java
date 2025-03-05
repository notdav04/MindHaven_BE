//package com.example.MindHaven_BE.service;
//
//
//import com.example.MindHaven_BE.exception.EmailDuplicateException;
//import com.example.MindHaven_BE.exception.UsernameDuplicateException;
//import com.example.MindHaven_BE.model.Professionista;
//import com.example.MindHaven_BE.model.Utente;
//import com.example.MindHaven_BE.payload.request.RegistrazioneRequest;
//import com.example.MindHaven_BE.repository.ProfessionistaDAORepository;
//import com.example.MindHaven_BE.repository.UtenteDAORepository;
//import com.example.MindHaven_BE.security.services.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class AuthService {
//
//    @Autowired
//    UtenteDAORepository userRepo;
//
//    @Autowired
//    ProfessionistaDAORepository professionistaRepo;
//
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtUtil jwtUtil;
//
//
//    public String newUser(RegistrazioneRequest registrazione){
//        String passwordCodificata = encoder.encode(registrazione.getPassword());
//        checkDuplicateKeyUser(registrazione.getUsername());
//        Utente user = registrazioneRequest_Utente(registrazione);
//        user.setPassword(passwordCodificata);
//        if ( registrazione.getRuolo() == null || registrazione.getRuolo().equals("USER")){
//            user.setRuolo("USER");
//        } else if(registrazione.getRuolo().equals("ADMIN")){
//            user.setRuolo("ADMIN");
//        }else{
//            throw new RuntimeException("Errore: il valore inserito come ruolo non è valido");
//        }
//
//        Long id = userRepo.save(user).getId();
//        return "Nuovo utente registrato con id: " + id;
//    }
//
////    public String newProfessionista(RegistrazioneRequest registrazione){
////
////        if (registrazione.getRuolo())
////        String passwordCodificata = encoder.encode(registrazione.getPassword());
////        checkDuplicateKeyUser(registrazione.getUsername());
////        Utente user = registrazioneRequest_Utente(registrazione);
////        user.setPassword(passwordCodificata);
////        if ( registrazione.getRuolo() == null || registrazione.getRuolo().equals(Erole.USER.name())){
////            user.setRuolo(Erole.USER);
////        } else if(registrazione.getRuolo().equals(Erole.ADMIN.name())){
////            user.setRuolo(Erole."ADMIN");
////        }else{
////            throw new RuntimeException("Errore: il valore inserito come ruolo non è valido");
////        }
////
////        Long id = userRepo.save(user).getId();
////        return "Nuovo utente registrato con id: " + id;
////
////    }
//
//
//
//
//    public void checkDuplicateKeyUser(String username) throws UsernameDuplicateException {
//        if (userRepo.existsByUsername(username)) {
//            throw new UsernameDuplicateException("Username già utilizzato, non disponibile");
//        }
//    }
//
//    public void checkDuplicateKeyProfessionista(String username, String email)throws UsernameDuplicateException, EmailDuplicateException{
//        if(professionistaRepo.existsByUsername(username)){
//            throw new UsernameDuplicateException("Username gia utilizzato, non disponibile");
//        } else if (professionistaRepo.existsByEmail(email)){
//            throw new EmailDuplicateException("Email gia utilizzata, non disponibile");
//        }
//    }
//
//
//
//    public Utente registrazioneRequest_Utente(RegistrazioneRequest request) {
//        Utente utente = new Utente();
//        utente.setUsername(request.getUsername());
//        return utente;
//    }
//
//    public Professionista registrazione_Professionista(RegistrazioneRequest reg){
//        Professionista professionista = new Professionista();
//        professionista.setNome(reg.getNome());
//        professionista.setCognome(reg.getCognome());
//        professionista.setEmail(reg.getEmail());
//        professionista.setUsername(reg.getUsername());
//        return professionista;
//
//    }
//
//
//
//}
