package com.gamehub.demo.repository;

import com.gamehub.demo.entity.Comment;
import com.gamehub.demo.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Map;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByGameOrderByCreatedAtDesc(Game game);

    @Query("SELECT c.game.title as label, COUNT(c) as value FROM Comment c GROUP BY c.game.title")
    List<Map<String, Object>> getCommentStatsPerGame();
}