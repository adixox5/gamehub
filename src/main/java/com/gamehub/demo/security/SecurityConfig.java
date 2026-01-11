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
                // Wyłączenie CSRF ułatwia pracę z formularzami w prostych projektach
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // --- 1. ZASOBY STATYCZNE (Kluczowe dla działania gier i wyglądu) ---
                        // "games/**" pozwala ładować pliki gier (2048, hextris itp.) w iframe
                        .requestMatchers("/style/**", "/js/**", "/css/**", "/images/**", "/games/**", "/static/**").permitAll()

                        // --- 2. STRONY DOSTĘPNE DLA WSZYSTKICH ---
                        // Dodaliśmy wersje z ".html" i bez, aby uniknąć błędów 404
                        .requestMatchers("/", "/index", "/index.html").permitAll()
                        .requestMatchers("/info", "/info.html").permitAll()
                        .requestMatchers("/regulamin", "/regulamin.html").permitAll()
                        .requestMatchers("/game", "/game.html").permitAll() // <--- TU BYŁ BŁĄD, TERAZ JEST OK
                        .requestMatchers("/category", "/category.html").permitAll()

                        // Logowanie i Rejestracja
                        .requestMatchers("/login", "/login.html", "/register", "/register.html").permitAll()
                        .requestMatchers("/auth/**").permitAll() // Endpointy backendowe

                        // --- 3. STRONY CHRONIONE (WYMAGAJĄ ZALOGOWANIA) ---
                        .requestMatchers("/add-game", "/add-game.html").authenticated()
                        .requestMatchers("/admin-panel", "/admin-panel.html").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Wszystko inne wymaga bycia zalogowanym
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Ścieżka do Twojego kontrolera zwracającego widok logowania
                        .loginProcessingUrl("/login") // Gdzie formularz wysyła dane POST
                        .defaultSuccessUrl("/", true) // Przekierowanie na stronę główną po sukcesie
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    // Bean potrzebny do szyfrowania haseł (jeśli używasz UserDetailsService)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}