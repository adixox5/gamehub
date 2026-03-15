package com.gamehub.demo.controller;

import com.gamehub.demo.entity.Game;
import com.gamehub.demo.entity.GameSubmission;
import com.gamehub.demo.repository.GameRepository;
import com.gamehub.demo.repository.GameSubmissionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/submissions")
public class AdminSubmissionController {

    private final GameSubmissionRepository submissionRepository;
    private final GameRepository gameRepository;

    public AdminSubmissionController(GameSubmissionRepository submissionRepository, GameRepository gameRepository) {
        this.submissionRepository = submissionRepository;
        this.gameRepository = gameRepository;
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approveSubmission(@PathVariable("id") Long id) {
        GameSubmission sub = submissionRepository.findById(id).orElseThrow();
        sub.setStatus("APPROVED");
        submissionRepository.save(sub);

        Game newGame = new Game();
        newGame.setTitle(sub.getTitle());
        newGame.setCategory(sub.getCategory());
        newGame.setGameUrl(sub.getGameLink());
        newGame.setImageUrl("/games/default.png");
        newGame.setDescription("Dodane przez: " + sub.getUsername());
        gameRepository.save(newGame);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectSubmission(@PathVariable("id") Long id) {
        GameSubmission sub = submissionRepository.findById(id).orElseThrow();
        sub.setStatus("REJECTED");
        submissionRepository.save(sub);
        return ResponseEntity.ok().build();
    }
}