package com.gamehub.demo.service;

import com.gamehub.demo.entity.User;
import com.gamehub.demo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerByEmail(String rawEmail, String rawDisplayName, String rawPassword) {
        String email = normalizeEmail(rawEmail);
        String displayName = normalizeDisplayName(rawDisplayName);

        if (userRepository.existsByUsername(email)) {
            throw new IllegalArgumentException("User already exists");
        }

        String hash = passwordEncoder.encode(rawPassword);
        User user = new User(email, displayName, hash, "user");
        userRepository.saveAndFlush(user);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameInput) throws UsernameNotFoundException {
        String email = normalizeEmail(usernameInput);

        User u = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = "ROLE_" + u.getRole().toUpperCase();

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPasswordHash(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }

    private static String normalizeEmail(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }

    private static String normalizeDisplayName(String displayName) {
        if (displayName == null) return null;
        String s = displayName.trim();
        return s.isBlank() ? null : s;
    }
}
