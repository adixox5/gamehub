package com.gamehub.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Zasoby statyczne
                        .requestMatchers("/style/**", "/js/**", "/css/**", "/images/**", "/games/**", "/static/**").permitAll()


                        .requestMatchers("/", "/index", "/index.html", "/error").permitAll() // <-- Dodano /error
                        .requestMatchers("/info.html", "/regulamin.html").permitAll()
                        .requestMatchers("/game.html").permitAll()
                        .requestMatchers("/category.html").permitAll()

                        // 3. Logowanie i Rejestracja
                        .requestMatchers("/login", "/login.html", "/register", "/register.html").permitAll()
                        .requestMatchers("/auth/**").permitAll()

                        // 4. Strony chronione
                        .requestMatchers("/add-game").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Wszystko inne wymaga logowania
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}