package com.gamehub.demo.security;

import com.gamehub.demo.entity.UserLoginLog;
import com.gamehub.demo.repository.UserLoginLogRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserLoginLogRepository repository;

    public LoginSuccessListener(UserLoginLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            repository.save(new UserLoginLog(username, LocalDateTime.now()));
        }
    }
}