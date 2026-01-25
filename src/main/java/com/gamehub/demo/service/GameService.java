package com.gamehub.demo.service;

import com.gamehub.demo.entity.Game;
import com.gamehub.demo.repository.GameRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        if (gameRepository.count() == 0) {
            gameRepository.save(new Game("2048", "Logiczne", "Połącz płytki, aby uzyskać 2048!", "/games/2048/style/img/favicon.ico", "/games/2048/index.html"));
            gameRepository.save(new Game("Hextris", "Logiczne", "Sześciokątny Tetris o szybkim tempie.", "/games/hextris/images/icons/apple-touch-120.png", "/games/hextris/index.html"));
            gameRepository.save(new Game("Clumsy Bird", "Zręcznościowe", "Lataj i omijaj rury (klon Flappy Bird).", "/games/clumsy/data/img/clumsy.png", "/games/clumsy/index.html"));
            gameRepository.save(new Game("Pacman", "Zręcznościowe", "Klasyczna gra zręcznościowa.", "/games/pacman/img/icon-128.png", "/games/pacman/index.htm"));
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