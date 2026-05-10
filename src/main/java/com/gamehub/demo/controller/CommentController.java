package com.gamehub.demo.controller;

import com.gamehub.demo.entity.Comment;
import com.gamehub.demo.entity.Game;
import com.gamehub.demo.repository.CommentRepository;
import com.gamehub.demo.repository.GameRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final GameRepository gameRepository;

    public CommentController(CommentRepository commentRepository, GameRepository gameRepository) {
        this.commentRepository = commentRepository;
        this.gameRepository = gameRepository;
    }

    @PostMapping("/add/{gameId}")
    public String addComment(@PathVariable Long gameId, @RequestParam String content, Authentication auth) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        // Jeśli użytkownik jest zalogowany, bierzemy jego email/login, w przeciwnym razie Anonim
        String author = (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) ? auth.getName() : "Anonim";

        commentRepository.save(new Comment(game, author, content));

        // POPRAWKA: Przekierowanie na adres z parametrem ?id=
        return "redirect:/game?id=" + gameId;
    }
}