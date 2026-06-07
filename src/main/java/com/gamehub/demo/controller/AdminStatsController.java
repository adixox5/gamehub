package com.gamehub.demo.controller;

import com.gamehub.demo.dto.DailyActivePlayers;
import com.gamehub.demo.dto.GamePlayCount;
import com.gamehub.demo.entity.UserLoginLog;
import com.gamehub.demo.entity.Game;
import com.gamehub.demo.repository.GameRecordRepository;
import com.gamehub.demo.repository.UserLoginLogRepository;
import com.gamehub.demo.repository.CommentRepository;
import com.gamehub.demo.repository.GameRepository; // <-- Import repozytorium gier
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/stats")
public class AdminStatsController {

    private final UserLoginLogRepository repository;
    private final CommentRepository commentRepository;
    private final GameRepository gameRepository; // <-- Pole dla repozytorium gier
    private final GameRecordRepository gameRecordRepository;

    public AdminStatsController(UserLoginLogRepository repository,
                                CommentRepository commentRepository,
                                GameRepository gameRepository,
                                GameRecordRepository gameRecordRepository) { // <-- Dodanie do konstruktora
        this.repository = repository;
        this.commentRepository = commentRepository;
        this.gameRepository = gameRepository;
        this.gameRecordRepository = gameRecordRepository;
    }

    @GetMapping("/logins")
    public Map<String, Object> getLoginStats() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        List<UserLoginLog> recentLogins = repository.findAllByLoginDateAfter(oneWeekAgo);

        int[] weekData = new int[7];
        for (UserLoginLog log : recentLogins) {
            int daysAgo = (int) java.time.Duration.between(log.getLoginDate(), LocalDateTime.now()).toDays();
            if (daysAgo >= 0 && daysAgo < 7) {
                weekData[6 - daysAgo]++;
            }
        }

        Map<String, Object> week = new HashMap<>();
        week.put("labels", Arrays.asList("7 dni temu", "6 dni temu", "5 dni temu", "4 dni temu", "3 dni temu", "Wczoraj", "Dzisiaj"));
        week.put("data", weekData);

        Map<String, Object> response = new HashMap<>();
        response.put("week", week);
        return response;
    }

    @GetMapping("/comments")
    public List<Map<String, Object>> getCommentStats() {
        return commentRepository.getCommentStatsPerGame();
    }

    // <-- NOWY ENDPOINT DLA LIKE/DISLIKE
    @GetMapping("/reactions")
    public List<Map<String, Object>> getGameReactions() {
        List<Game> games = gameRepository.findAll();
        List<Map<String, Object>> stats = new ArrayList<>();

        for (Game game : games) {
            Map<String, Object> gameStat = new HashMap<>();
            gameStat.put("title", game.getTitle());
            gameStat.put("likes", game.getLikes());
            gameStat.put("dislikes", game.getDislikes());
            stats.add(gameStat);
        }
        return stats;
    }

    @GetMapping("/most-played-games")
    public List<GamePlayCount> getMostPlayedGames() {
        return gameRecordRepository.findGamePlayCounts().stream().limit(10).collect(Collectors.toList());
    }

    @GetMapping("/daily-active-players")
    public List<DailyActivePlayers> getDailyActivePlayers() {
        LocalDateTime since = LocalDateTime.now().minusDays(30);
        return gameRecordRepository.countDailyActivePlayers(since);
    }
}