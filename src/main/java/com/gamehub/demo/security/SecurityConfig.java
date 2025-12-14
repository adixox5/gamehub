package com.gamehub.demo.security;

import com.gamehub.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder encoder) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(userService);
        p.setPasswordEncoder(encoder);
        return p;
    }

    @Bean
    org.springframework.security.authentication.AuthenticationManager authenticationManager(AuthenticationConfiguration cfg)
            throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index",

                                // статические страницы и ресурсы
                                "/pages/**",
                                "/js/**",
                                "/style/**",
                                "/css/**",
                                "/static/**",

                                // регистрация из формы
                                "/auth/register"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // страница логина — статический файл
                        .loginPage("/pages/login.html")
                        // обработка логина (POST /login) с полями username/password
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        // если логин неуспешный — вернёт на логин-страницу с флагом
                        .failureUrl("/pages/login.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/pages/login.html?logout=true")
                        .permitAll()
                )
                // чтобы не вылезало окно браузера "войдите в систему"
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}