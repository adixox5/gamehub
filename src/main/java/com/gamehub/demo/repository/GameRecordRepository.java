package com.gamehub.demo.repository;

import com.gamehub.demo.entity.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {
    List<GameRecord> findByUsernameOrderByScoreDesc(String username);
    List<GameRecord> findTop10ByGameTitleOrderByScoreDesc(String gameTitle);
}