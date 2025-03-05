package com.example.MindHaven_BE.security.services;

import com.example.MindHaven_BE.exception.CreateTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path  = request.getRequestURI();
        if (path.startsWith("/auth")){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 1. RECUPERO DEL TOKEN
            String token = jwtUtil.recuperoToken(request);

            // 2. VALIDAZIONE DEL TOKEN : TOKEN -> CLAIMS
            Claims claims = jwtUtil.validaClaims(request);

            // 3. CHECK SCADENZA
            if(claims!=null && jwtUtil.checkExpiration(claims)){

                List<GrantedAuthority> ruoli = new ArrayList<>();
                GrantedAuthority ruoloAutenticato = new SimpleGrantedAuthority(claims.get("roles").toString());
                ruoli.add(ruoloAutenticato);

                // 4. INTEGRITA' DEL TOKEN
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(claims.getSubject(), "", ruoli);

                // 5. INSERIMENTO NEL CONTESTO DI SICUREZZA
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

        } catch(CreateTokenException ex){
            filterChain.doFilter(request,response);
            return;
        } catch(Exception e) {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", "Authenticazione negata");
            errorDetails.put("details", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), errorDetails);

        }

        filterChain.doFilter(request,response);

    }
}
