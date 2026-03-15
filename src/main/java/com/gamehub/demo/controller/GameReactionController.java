package com.gamehub.demo.controller;

import com.gamehub.demo.entity.Game;
import com.gamehub.demo.repository.GameRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/games")
public class GameReactionController {

    private final GameRepository gameRepository;

    public GameReactionController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Integer> likeGame(@PathVariable Long id) {
        Optional<Game> gameOpt = gameRepository.findById(id);
        if (gameOpt.isPresent()) {
            Game game = gameOpt.get();
            game.setLikes(game.getLikes() + 1);
            gameRepository.save(game);
            return ResponseEntity.ok(game.getLikes());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Integer> dislikeGame(@PathVariable Long id) {
        Optional<Game> gameOpt = gameRepository.findById(id);
        if (gameOpt.isPresent()) {
            Game game = gameOpt.get();
            game.setDislikes(game.getDislikes() + 1);
            gameRepository.save(game);
            return ResponseEntity.ok(game.getDislikes());
        }
        return ResponseEntity.notFound().build();
    }
}