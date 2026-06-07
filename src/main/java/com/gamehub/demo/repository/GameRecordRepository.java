// src/main/java/com/gamehub/demo/repository/GameRecordRepository.java
package com.gamehub.demo.repository;

import com.gamehub.demo.dto.DailyActivePlayers;
import com.gamehub.demo.dto.GamePlayCount;
import com.gamehub.demo.entity.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {
    List<GameRecord> findByUsernameOrderByScoreDesc(String username);
    List<GameRecord> findAllByUsernameOrderByScoreDesc(String username);
    List<GameRecord> findByGameTitleOrderByScoreDesc(String gameTitle);

    @Query("SELECT new com.gamehub.demo.dto.GamePlayCount(gr.gameTitle, COUNT(gr)) FROM GameRecord gr GROUP BY gr.gameTitle ORDER BY COUNT(gr) DESC")
    List<GamePlayCount> findGamePlayCounts();

    @Query("SELECT new com.gamehub.demo.dto.DailyActivePlayers(CAST(gr.recordDate AS LocalDate), COUNT(DISTINCT gr.username)) FROM GameRecord gr WHERE gr.recordDate >= :since GROUP BY CAST(gr.recordDate AS LocalDate) ORDER BY CAST(gr.recordDate AS LocalDate) ASC")
    List<DailyActivePlayers> countDailyActivePlayers(@Param("since") LocalDateTime since);
}