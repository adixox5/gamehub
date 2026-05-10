package com.gamehub.demo.controller;

import com.gamehub.demo.entity.Comment;
import com.gamehub.demo.entity.Game;
import com.gamehub.demo.entity.GameRecord;
import com.gamehub.demo.entity.User;
import com.gamehub.demo.repository.CommentRepository;
import com.gamehub.demo.repository.GameRecordRepository;
import com.gamehub.demo.repository.GameRepository;
import com.gamehub.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PageController {

    private final GameRepository gameRepository;
    private final GameRecordRepository gameRecordRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public PageController(GameRepository gameRepository,
                          GameRecordRepository gameRecordRepository,
                          UserService userService,
                          CommentRepository commentRepository) {
        this.gameRepository = gameRepository;
        this.gameRecordRepository = gameRecordRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String search, Model model) {
        List<Game> games;
        if (search != null && !search.isBlank()) {
            games = gameRepository.findAll().stream()
                    .filter(g -> g.getTitle().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            games = gameRepository.findAll();
        }
        model.addAttribute("games", games);

        List<Game> allGamesForSlider = gameRepository.findAll();
        // Tasujemy listę (losowa kolejność)
        Collections.shuffle(allGamesForSlider);
        // Wybieramy maksymalnie 3 pierwsze z potasowanej listy
        List<Game> featuredGames = allGamesForSlider.stream()
                .limit(3)
                .collect(Collectors.toList());

        // Przekazujemy wylosowane gry do widoku (do HTML)
        model.addAttribute("featuredGames", featuredGames);

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/game")
    public String gamePage(@RequestParam Long id, Model model) {
        Game game = gameRepository.findById(id).orElse(null);
        model.addAttribute("game", game);

        if (game != null) {
            List<Comment> comments = commentRepository.findByGameOrderByCreatedAtDesc(game);
            model.addAttribute("comments", comments);
        }

        return "game";
    }

    @GetMapping("/category")
    public String categoryPage(@RequestParam(required = false) String name, Model model) {
        List<Game> games;
        if (name != null && !name.isBlank()) {
            games = gameRepository.findAll().stream()
                    .filter(g -> g.getCategory().equalsIgnoreCase(name))
                    .collect(Collectors.toList());
            model.addAttribute("categoryName", name);
        } else {
            games = gameRepository.findAll();
            model.addAttribute("categoryName", "Wszystkie");
        }
        model.addAttribute("games", games);
        return "category";
    }

    @GetMapping("/info")
    public String infoPage() {
        return "info";
    }

    @GetMapping("/regulamin")
    public String regulaminPage() {
        return "regulamin";
    }

    @GetMapping("/profile")
    public String profilePage(Authentication auth, Model model) {
        if (auth == null) return "redirect:/login";

        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);

        List<GameRecord> records = gameRecordRepository.findAllByUsernameOrderByScoreDesc(user.getUsername());
        model.addAttribute("records", records);

        return "profile";
    }

    @GetMapping("/add-game")
    public String addGamePage() {
        return "add-game";
    }

    @GetMapping("/admin")
    public String adminPanel() {
        return "admin-panel";
    }
}