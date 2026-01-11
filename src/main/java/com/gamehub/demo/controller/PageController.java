package com.gamehub.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/add-game")
    public String addGame() {
        return "add-game";
    }

    @GetMapping("/game")
    public String game() {
        return "game";
    }

    @GetMapping("/category")
    public String category() {
        return "category";
    }

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/regulamin")
    public String regulamin() {
        return "regulamin";
    }

    @GetMapping("/admin/panel")
    public String adminPanel() {
        return "admin-panel";
    }
}