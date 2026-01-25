package com.gamehub.demo.controller;

import com.gamehub.demo.entity.Game;
import com.gamehub.demo.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {

    private final GameService gameService;

    // Wstrzykujemy GameService przez konstruktor
    public PageController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "category", required = false) String category, Model model) {
        // 1. Pobierz gry (wszystkie lub filtrowane)
        List<Game> games = gameService.getGamesByCategory(category);

        // 2. Pobierz listę kategorii do menu
        List<String> categories = gameService.getAllCategories();

        // 3. Przekaż dane do widoku HTML
        model.addAttribute("games", games);
        model.addAttribute("categories", categories);
        model.addAttribute("currentCategory", category);

        return "index";
    }

    @GetMapping("/index.html")
    public String indexHtml(@RequestParam(name = "category", required = false) String category, Model model) {
        return index(category, model);
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

    @GetMapping("/game.html")
    public String game() {
        return "game";
    }
}