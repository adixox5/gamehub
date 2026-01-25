package com.gamehub.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    private String description;

    private String imageUrl; // Ścieżka do ikony gry

    private String gameUrl;  // Ścieżka do pliku index.html gry

    public Game() {
    }

    public Game(String title, String category, String description, String imageUrl, String gameUrl) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
        this.gameUrl = gameUrl;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getGameUrl() { return gameUrl; }
    public void setGameUrl(String gameUrl) { this.gameUrl = gameUrl; }
}