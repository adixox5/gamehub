// demo/src/main/java/com/gamehub/demo/controller/IndexController.java
package com.gamehub.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/game")
    public String game() {
        return "game";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}