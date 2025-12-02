package com.medilabo.assessment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuration de la sécurité pour l'application d'évaluation des risques.
 * Définit un utilisateur en mémoire avec le rôle GATEWAY et configure la sécurité HTTP.
 */
@Configuration
@EnableWebSecurity
public class AssessmentSecurityConfig {

    /**
     * Définit un service de détails utilisateur en mémoire avec un utilisateur par défaut.
     * L'utilisateur a le nom d'utilisateur "user", le mot de passe "user" (en clair pour les tests)
     * et le rôle "GATEWAY".
     *
     * @return un UserDetailsService avec l'utilisateur configuré.
     */
    @Bean
    public UserDetailsService users() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        userDetailsService.createUser(User
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("GATEWAY")
                .build());

        return userDetailsService;
    }

    /**
     * Configure la chaîne de filtres de sécurité HTTP.
     * Désactive la protection CSRF, permet toutes les requêtes (à modifier selon les besoins)
     * et active l'authentification HTTP Basic.
     *
     * @param http l'objet HttpSecurity à configurer.
     * @return la chaîne de filtres de sécurité construite.
     * @throws Exception en cas d'erreur de configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .anyRequest().permitAll()
                )
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
