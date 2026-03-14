package com.gamehub.demo.controller;

import com.gamehub.demo.entity.UserLoginLog;
import com.gamehub.demo.repository.UserLoginLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/admin/stats")
public class AdminStatsController {

    private final UserLoginLogRepository repository;

    public AdminStatsController(UserLoginLogRepository repository) {
        this.repository = repository;
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
        week.put("labels", Arrays.asList("Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Today"));
        week.put("data", weekData);

        Map<String, Object> month = new HashMap<>();
        month.put("labels", Arrays.asList("Week 1", "Week 2", "Week 3", "Week 4"));
        month.put("data", Arrays.asList(0, 0, 0, Arrays.stream(weekData).sum()));

        Map<String, Object> year = new HashMap<>();
        year.put("labels", Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
        year.put("data", Arrays.asList(0, 0, Arrays.stream(weekData).sum(), 0, 0, 0, 0, 0, 0, 0, 0, 0));

        Map<String, Object> response = new HashMap<>();
        response.put("week", week);
        response.put("month", month);
        response.put("year", year);

        return response;
    }
}