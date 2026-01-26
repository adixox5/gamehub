package com.gamehub.demo.service;

import com.gamehub.demo.entity.Game;
import com.gamehub.demo.repository.GameRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    @Transactional
    public void initGames() {
        // 1. Inicjalizacja podstawowych gier
        if (gameRepository.count() == 0) {
            gameRepository.save(new Game("2048", "Logiczne", "Połącz płytki, aby uzyskać 2048!", "/games/2048/style/img/favicon.ico", "/games/2048/index.html"));
            gameRepository.save(new Game("Hextris", "Logiczne", "Sześciokątny Tetris o szybkim tempie.", "/games/hextris/images/icons/apple-touch-120.png", "/games/hextris/index.html"));
            gameRepository.save(new Game("Clumsy Bird", "Zręcznościowe", "Lataj i omijaj rury (klon Flappy Bird).", "/games/clumsy/data/img/clumsy.png", "/games/clumsy/index.html"));
            gameRepository.save(new Game("Pacman", "Zręcznościowe", "Klasyczna gra zręcznościowa.", "/games/pacman/img/icon-128.png", "/games/pacman/index.htm"));
        }

        // 2. Obsługa Tetrisa
        if (gameRepository.findByTitleContainingIgnoreCase("Tetris").isEmpty()) {
            gameRepository.save(new Game("Tetris", "Logiczne", "Klasyczny Tetris w HTML5 Canvas.", "/games/tetris/icon.png", "/games/tetris/index.html"));
            System.out.println("Dodano grę Tetris.");
        }

        List<Game> badOhh1 = gameRepository.findByTitleContainingIgnoreCase("Ohh1");

        boolean correctVersionExists = false;

        for (Game g : badOhh1) {
            if (g.getGameUrl().contains("/games/ohh1/")) {
                g.setTitle("0hh1"); // Poprawny tytuł (opcjonalnie)
                g.setImageUrl("/games/0hh1/icon.png"); // Poprawna ścieżka (zero)
                g.setGameUrl("/games/0hh1/index.html"); // Poprawna ścieżka (zero)
                gameRepository.save(g);
                System.out.println("Naprawiono ścieżki dla gry 0hh1.");
                correctVersionExists = true;
            } else if (g.getGameUrl().contains("/games/0hh1/")) {
                correctVersionExists = true;
            }
        }

        // Jeśli gra w ogóle nie istnieje, dodaj ją poprawnie (przez '0')
        if (!correctVersionExists) {
            gameRepository.save(new Game(
                    "0hh1",
                    "Logiczne",
                    "Uzupełnij planszę kolorami (niebieskie i czerwone).",
                    "/games/0hh1/icon.png",
                    "/games/0hh1/index.html"
            ));
            System.out.println("Dodano grę 0hh1.");
        }
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> getGamesByCategory(String category) {
        if (category == null || category.isBlank() || category.equals("all")) {
            return gameRepository.findAll();
        }
        return gameRepository.findByCategory(category);
    }

    public List<String> getAllCategories() {
        return gameRepository.findDistinctCategories();
    }

    public List<String> getAllGameTitles() {
        return gameRepository.findAll().stream()
                .map(Game::getTitle)
                .collect(Collectors.toList());
    }

    public List<Game> getGames(String category, String search) {
        List<Game> games;

        if (search != null && !search.isBlank()) {
            if (category != null && !category.isBlank() && !category.equals("all")) {
                games = gameRepository.findByCategoryAndTitleContainingIgnoreCase(category, search);
            } else {
                games = gameRepository.findByTitleContainingIgnoreCase(search);
            }
        } else if (category != null && !category.isBlank() && !category.equals("all")) {
            games = gameRepository.findByCategory(category);
        } else {
            games = gameRepository.findAll();
        }

        return games;
    }
}