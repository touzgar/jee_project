	package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {

        AuthenticationManager authMgr = authConfig.getAuthenticationManager();

        http
            .cors().and() // Enable CORS (configured in CorsConfig.java)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login", "/api/user/createUser").permitAll()
            .antMatchers("/api/user/**").hasAuthority("ADMIN")
            .antMatchers("/api/fournisseur/create", "/api/fournisseur/update/**", "/api/fournisseur/delete/**").hasAuthority("ADMIN")
            .antMatchers("/api/fournisseur/**").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/produit/create", "/api/produit/update/**", "/api/produit/delete/**", "/api/produit/update-stock/**").hasAuthority("ADMIN")
            .antMatchers("/api/produit/**").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/transporteur/create", "/api/transporteur/update/**", "/api/transporteur/delete/**").hasAuthority("ADMIN")
            .antMatchers("/api/transporteur/**").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/commande/**").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/lignecommande/**").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/livraison/create", "/api/livraison/update/**", "/api/livraison/delete/**").hasAuthority("ADMIN")
            .antMatchers("/api/livraison/**").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/paiement/update/**", "/api/paiement/delete/**").hasAuthority("ADMIN")
            .antMatchers("/api/paiement/**").hasAnyAuthority("ADMIN", "USER")
            .anyRequest().authenticated();

        http.addFilterBefore(new JWTAuthenticationFilter(authMgr), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}