package com.gamehub.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index.html")
    public String indexHtml() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/add-game")
    public String addGame() {
        return "add-game";
    }

    @GetMapping("/info.html")
    public String info() {
        return "info";
    }

    @GetMapping("/regulamin.html")
    public String regulamin() {
        return "regulamin";
    }

    // --- KLUCZOWE: Obsługa gry ---
    // Dzięki temu adres /game.html wyświetli plik templates/game.html
    @GetMapping("/game.html")
    public String game() {
        return "game";
    }
}