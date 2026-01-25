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

    public PageController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "category", required = false) String category,
                        @RequestParam(name = "search", required = false) String search,
                        Model model) {
        List<Game> games = gameService.getGames(category, search);
        List<String> categories = gameService.getAllCategories();

        model.addAttribute("games", games);
        model.addAttribute("categories", categories);
        model.addAttribute("currentCategory", category);
        model.addAttribute("search", search);

        return "index";
    }

    @GetMapping("/index.html")
    public String indexHtml(@RequestParam(name = "category", required = false) String category,
                            @RequestParam(name = "search", required = false) String search,
                            Model model) {
        return index(category, search, model);
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
