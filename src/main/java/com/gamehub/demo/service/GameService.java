package com.gamehub.demo.service;

import com.gamehub.demo.entity.Game;
import com.gamehub.demo.repository.GameRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Metoda uruchamia się przy starcie aplikacji
    @PostConstruct
    @Transactional
    public void initGames() {
        if (gameRepository.count() == 0) {
            // Dodajemy gry wykryte w Twoich plikach
            gameRepository.save(new Game("2048", "Logiczne", "Połącz płytki, aby uzyskać 2048!", "/games/2048/style/img/favicon.ico", "/games/2048/index.html"));
            gameRepository.save(new Game("Hextris", "Logiczne", "Sześciokątny Tetris o szybkim tempie.", "/games/hextris/images/icons/apple-touch-120.png", "/games/hextris/index.html"));
            gameRepository.save(new Game("Clumsy Bird", "Zręcznościowe", "Lataj i omijaj rury (klon Flappy Bird).", "/games/clumsy/data/img/clumsy.png", "/games/clumsy/index.html"));
            gameRepository.save(new Game("Pacman", "Klasyczne", "Zjedz kropki i unikaj duchów.", "/games/pacman/img/Pacman-Icon.svg", "/games/pacman/index.htm"));
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
    public List<Game> getGames(String category, String search) {
        // 1. Jeśli jest wyszukiwanie
        if (search != null && !search.isBlank()) {
            // 1a. Jeśli jest też kategoria, szukaj wewnątrz kategorii
            if (category != null && !category.isBlank() && !category.equals("all")) {
                return gameRepository.findByCategoryAndTitleContainingIgnoreCase(category, search);
            }
            // 1b. Szukaj we wszystkich grach
            return gameRepository.findByTitleContainingIgnoreCase(search);
        }

        // 2. Jeśli nie ma wyszukiwania, ale jest kategoria
        if (category != null && !category.isBlank() && !category.equals("all")) {
            return gameRepository.findByCategory(category);
        }

        // 3. Zwróć wszystko
        return gameRepository.findAll();
    }
}
