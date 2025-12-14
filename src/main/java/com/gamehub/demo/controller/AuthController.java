package com.gamehub.demo.controller;

import com.gamehub.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    /**
     * Регистрация из HTML-формы (x-www-form-urlencoded).
     *
     * ВАЖНО: фронт отправляет email, но мы храним логин в поле username => username=email.
     * TODO EnterRealBD: если захочешь отдельное поле email — добавим его в Entity и репозиторий.
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerForm(
            @RequestParam("username") String emailAsUsername,
            @RequestParam("password") String password
    ) {
        String email = (emailAsUsername == null) ? null : emailAsUsername.trim().toLowerCase();
        log.info("REGISTER /auth/register email='{}'", email);

        userService.registerByEmail(email, null, password);

        // ВАЖНО: чтобы не было белого экрана — редирект обратно на страницу логина
        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, "/pages/login.html?registered=true")
                .build();
    }
}
