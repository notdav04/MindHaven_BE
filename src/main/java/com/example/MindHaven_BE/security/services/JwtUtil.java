package com.example.MindHaven_BE.security.services;

import com.example.MindHaven_BE.exception.CreateTokenException;
import com.example.MindHaven_BE.model.Utente;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    // possono essere inserite anche nel properties

    private String JWTSECRET ="5d23f5d1b52b0672761877f7cad5b26365d6c8df4b89a1a7c12aa2c69df0ace286162b249d1fb8478e694ef1bc8580a3701fcc887207522ccb65e03414aa8e5b18b6099d21e49097c0dd461bab67f0b53ff0cbd9a2c0897bdd5e22e8abce84ee0f76e41b53227e8f94f2441e8e02161a5128701c3f8fa84ec6e9169ce0b2e7399e455dbc7366854a2d895f2eecb78b90066c619732fd35218e96cdeb8b18cf8f1f5da85a086c71789ee27319f58028711d151dbda18b8079c4f99663ee2087bb7d78c2d7f8aa345d6f52685cbb8729f66b4347137883e477e0252bbc0742facb926475b256b3860c680610ce34b40094a442f730521755ada2567aed626fd063";
    private long scadenza = 15;
    private final String TOKEN_HEADER ="Authorization";
    private final String TOKEN_PREFIX ="Bearer ";

    // Oggetto che occorre per la validazione
    private final JwtParser JWTPARSER;

    public JwtUtil(){
        JWTPARSER = Jwts.parser().setSigningKey(JWTSECRET);
    }

    /**
     * Metodo di creazione Token.
     * Recupera le info da Utente e le inserisce nel Token finale in formato String
     * @param utente
     * @return il token in formato String
     */
    public String creaToken(Utente utente){
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
