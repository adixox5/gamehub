package com.gamehub.demo.controller;

import com.gamehub.demo.entity.Game;
import com.gamehub.demo.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    private final GameService gameService;

    // Wstrzykujemy serwis, żeby móc pobierać gry z bazy
    public IndexController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "category", required = false) String category,
                        @RequestParam(name = "search", required = false) String search,
                        Model model) {

        // 1. Pobieramy gry pasujące do wyszukiwania i kategorii
        List<Game> games = gameService.getGames(category, search);

        // 2. Pobieramy listę wszystkich kategorii do menu rozwijanego
        List<String> categories = gameService.getAllCategories();

        // 3. Pakujemy wszystko do "pudełka" (Modelu), które otworzy strona HTML
        model.addAttribute("games", games);
        model.addAttribute("categories", categories);
        model.addAttribute("currentCategory", category);
        model.addAttribute("search", search);

        return "index";
    }
}