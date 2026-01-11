package com.gamehub.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
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

    // --- DODAJ TEN FRAGMENT PONIŻEJ ---
    @GetMapping("/game.html")
    public String game() {
        return "game"; // To mówi Springowi: "wyświetl plik templates/game.html"
    }
    // ----------------------------------

    // Jeśli masz inne podstrony (np. info, regulamin), też dodaj je tutaj:
    @GetMapping("/info.html")
    public String info() { return "info"; }

    @GetMapping("/regulamin.html")
    public String regulamin() { return "regulamin"; }
}