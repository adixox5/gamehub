package com.gamehub.demo.controller;

import com.gamehub.demo.entity.GameRecord;
import com.gamehub.demo.repository.GameRecordRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/records")
public class GameRecordController {

    private final GameRecordRepository recordRepository;

    public GameRecordController(GameRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveRecord(@RequestBody Map<String, Object> payload, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        String username = principal.getName();
        String gameTitle = (String) payload.get("gameTitle");
        int score = Integer.parseInt(payload.get("score").toString());

        recordRepository.save(new GameRecord(username, gameTitle, score));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<GameRecord>> getUserRecords(@PathVariable("username") String username) {
        return ResponseEntity.ok(recordRepository.findByUsernameOrderByScoreDesc(username));
    }

    @GetMapping("/top/{gameTitle}")
    public ResponseEntity<List<GameRecord>> getTopRecords(@PathVariable("gameTitle") String gameTitle) {
        List<GameRecord> allRecords = recordRepository.findByGameTitleOrderByScoreDesc(gameTitle);
        List<GameRecord> topUnique = new ArrayList<>();
        Set<String> seenUsers = new HashSet<>();

        for (GameRecord record : allRecords) {
            if (seenUsers.add(record.getUsername())) {
                topUnique.add(record);
                if (topUnique.size() == 10) {
                    break;
                }
            }
        }
        return ResponseEntity.ok(topUnique);
    }
}