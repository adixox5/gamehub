
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
    public String index(@RequestParam(required = false) String search,
                        @RequestParam(required = false) String category,
                        Model model) {

        List<Game> allGames = gameRepository.findAll();

        List<String> categories = allGames.stream()
                .map(Game::getCategory)
                .filter(c -> c != null && !c.isBlank())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<Game> games = allGames.stream()
                .filter(g -> {
                    boolean matchSearch = (search == null || search.isBlank() || g.getTitle().toLowerCase().contains(search.toLowerCase()));
                    boolean matchCategory = (category == null || category.isBlank() || g.getCategory().equalsIgnoreCase(category));
                    return matchSearch && matchCategory;
                })
                .collect(Collectors.toList());

        List<String> suggestionTitles = allGames.stream()
                .map(Game::getTitle)
                .collect(Collectors.toList());

        model.addAttribute("games", games);
        model.addAttribute("categories", categories);
        model.addAttribute("currentCategory", category);
        model.addAttribute("search", search);
        model.addAttribute("suggestionTitles", suggestionTitles);

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