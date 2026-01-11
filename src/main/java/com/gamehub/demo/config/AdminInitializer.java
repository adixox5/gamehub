package com.gamehub.demo.config;

import com.gamehub.demo.entity.User;
import com.gamehub.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@gamehub.com";

        if (!userRepository.existsByUsername(adminEmail)) {
            // Tworzenie użytkownika z rolą "admin"
            User admin = new User(
                    adminEmail,
                    "Administrator",
                    passwordEncoder.encode("admin123"), // Hasło administratora
                    "admin" // Rola, która w kodzie User.java zostanie zamieniona na małe litery
            );
            userRepository.save(admin);
            System.out.println("Utworzono konto administratora: " + adminEmail);
        }
    }
}