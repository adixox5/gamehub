package com.gamehub.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "game_submissions")
public class GameSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate submissionDate = LocalDate.now();

    private String title;

    private String username;

    private String category;

    private String gameLink;

    private String status = "PENDING";

    public GameSubmission() {}

    public GameSubmission(String title, String username, String category, String gameLink) {
        this.title = title;
        this.username = username;
        this.category = category;
        this.gameLink = gameLink;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDate submissionDate) { this.submissionDate = submissionDate; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getGameLink() { return gameLink; }
    public void setGameLink(String gameLink) { this.gameLink = gameLink; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}