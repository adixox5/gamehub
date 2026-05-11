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
            String title = game.getTitle().toLowerCase();

            if (title.contains("2048")) {
                game.setImageUrl("/Images/2048-game.png");
                gameRepository.save(game);
            } else if (title.contains("pacman")) {
                game.setImageUrl("/Images/images.jpg");
                gameRepository.save(game);
            } else if (title.contains("hextris")) {
                game.setImageUrl("/Images/facebook-promotion.png");
                gameRepository.save(game);
            }
        }
    }
}