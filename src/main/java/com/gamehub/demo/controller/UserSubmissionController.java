package com.gamehub.demo.controller;

import com.gamehub.demo.entity.GameSubmission;
import com.gamehub.demo.repository.GameSubmissionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
public class UserSubmissionController {

    private final GameSubmissionRepository submissionRepository;

    public UserSubmissionController(GameSubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @PostMapping("/api/submissions/add")
    public String addSubmission(
            @RequestParam("title") String title,
            @RequestParam("category") String category,
            @RequestParam("gameLink") String gameLink,
            Principal principal) {
        String username = principal != null ? principal.getName() : "Anonim";
        submissionRepository.save(new GameSubmission(title, username, category, gameLink));
        return "redirect:/";
    }
}