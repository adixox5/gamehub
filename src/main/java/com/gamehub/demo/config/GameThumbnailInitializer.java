package com.gamehub.demo.config;

import com.gamehub.demo.entity.Game;
import com.gamehub.demo.repository.GameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameThumbnailInitializer implements CommandLineRunner {

    private final GameRepository gameRepository;

    public GameThumbnailInitializer(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Game> games = gameRepository.findAll();

        for (Game game : games) {
            if ("2048".equalsIgnoreCase(game.getTitle())) {
                game.setImageUrl("/images/2048-game.png");
                gameRepository.save(game);
            } else if ("0hh1".equalsIgnoreCase(game.getTitle())) {
                game.setImageUrl("/images/facebook-promotion.png");
                gameRepository.save(game);
            } else if ("Tetris".equalsIgnoreCase(game.getTitle())) {
                game.setImageUrl("/images/images.jpg");
                gameRepository.save(game);
            }
        }
    }
}