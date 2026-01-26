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
                .csrf(csrf -> csrf.disable()) // Wyłączenie CSRF dla uproszczenia
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/style/**", "/js/**", "/css/**", "/images/**", "/games/**", "/static/**").permitAll()

                        // 2. Strony publiczne (Dostępne dla każdego)
                        .requestMatchers("/", "/index", "/index.html").permitAll()
                        .requestMatchers("/info.html", "/regulamin.html").permitAll()
                        .requestMatchers("/game.html").permitAll() // <--- TO NAPRAWIA PROBLEM PRZEKIEROWANIA
                        .requestMatchers("/category.html").permitAll()

                        // 3. Logowanie i Rejestracja
                        .requestMatchers("/login", "/login.html", "/register", "/register.html").permitAll()
                        .requestMatchers("/auth/**").permitAll()

                        // 4. Strony chronione (wymagają logowania)
                        .requestMatchers("/add-game").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Wszystko inne wymaga bycia zalogowanym
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}