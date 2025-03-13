package com.example.MindHaven_BE.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    JwtAuthorizationFilter filtroAutorizzazione;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager gestoreAuth(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder auth= httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(c->c.configurationSource(cors->{
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("http://localhost:5173");
            config.addAllowedMethod("*");
            config.addAllowedHeader("*");
            return config;
        }));
//        httpSecurity.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable);
        httpSecurity.csrf(c -> c.disable());
        httpSecurity.formLogin(f-> f.disable());
        // Impostazione autorizzazioni sugli accessi
        // REGISTRAZIONE senza autorizzazioni
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/utente/**").hasRole("USER")
                        .requestMatchers("/professionista/**").hasRole("PROFESSIONISTA")
                .requestMatchers("/user/admin/**").hasRole("ADMIN"))
                .sessionManagement(custom->custom.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filtroAutorizzazione, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


}
