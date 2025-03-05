package com.example.MindHaven_BE.security.services;

import com.example.MindHaven_BE.exception.CreateTokenException;
import com.example.MindHaven_BE.model.Professionista;
import com.example.MindHaven_BE.model.Utente;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {


@Value("${jwt.secret}")
    private String JWTSECRET;
    private long scadenza = 15;
    private final String TOKEN_HEADER ="Authorization";
    private final String TOKEN_PREFIX ="Bearer ";

    // Oggetto che occorre per la validazione
    private  JwtParser JWTPARSER;

    @PostConstruct
    public void init() {
        JWTPARSER = Jwts.parserBuilder().setSigningKey(JWTSECRET).build();
    }
//    public JwtUtil(){
//        JWTPARSER = Jwts.parser().setSigningKey(JWTSECRET);
//    }

    /**
     * Metodo di creazione Token.
     * Recupera le info da Utente e le inserisce nel Token finale in formato String
     * @param utente
     * @return il token in formato String
     */
    public String creaToken(Utente utente ){
        // Impostazione del Claims (Payload)
        Claims claims = Jwts.claims().setSubject(utente.getUsername());
        claims.put("roles", utente.getRuolo());

        Date dataCreazioneToken = new Date();
        Date dataScadenza = new Date(dataCreazioneToken.getTime() + TimeUnit.MINUTES.toMillis(scadenza));

        // CREAZIONE TOKEN : claims, data expiration, firma con tipologi algoritmo e la chiave
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(dataScadenza)
                .signWith(SignatureAlgorithm.HS256, JWTSECRET)
                .compact();

        return token;
    }
    public String creaToken(Professionista professionista ){
        // Impostazione del Claims (Payload)
        Claims claims = Jwts.claims().setSubject(professionista.getUsername());
        claims.put("roles", professionista.getRuolo());

        Date dataCreazioneToken = new Date();
        Date dataScadenza = new Date(dataCreazioneToken.getTime() + TimeUnit.MINUTES.toMillis(scadenza));

        // CREAZIONE TOKEN : claims, data expiration, firma con tipologi algoritmo e la chiave
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(dataScadenza)
                .signWith(SignatureAlgorithm.HS256, JWTSECRET)
                .compact();

        return token;
    }

    /**
     * Estrazione del TOKEN in arrivo all'interno della request
     * @param request
     * @return
     */
    public String recuperoToken(HttpServletRequest request) throws CreateTokenException {

        // Recupero dall'header della richiesta il token con prefisso
        String bearerToken = request.getHeader(TOKEN_HEADER);

        // Il token è presente? Inizia con "Bearer " ?
        if(bearerToken!=null && bearerToken.startsWith(TOKEN_PREFIX)){
            // Ritorna il token senza prefisso
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        throw new CreateTokenException();
    }

    /**
     * Metodo di validazione del Token.
     * Recupera il Token e ne estra solo il payload.
     * @param request
     * @return
     * @throws CreateTokenException
     */
    public Claims validaClaims(HttpServletRequest request) throws CreateTokenException {
        try {
            String token = recuperoToken(request);
            return JWTPARSER.parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException ex){
            request.setAttribute("expired", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            request.setAttribute("token invalido", ex.getMessage());
            throw ex;
        }
    }

    /**
     * Metodo che controlla la scadenza del Token
     * @param claims
     * @return
     */
    public boolean checkExpiration(Claims claims){
        try{
            return claims.getExpiration().after(new Date());
        }catch(Exception ex){
            throw ex;
        }

    }

}
