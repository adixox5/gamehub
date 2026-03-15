package com.gamehub.demo.repository;

import com.gamehub.demo.entity.GameSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameSubmissionRepository extends JpaRepository<GameSubmission, Long> {
    List<GameSubmission> findByStatus(String status);
    long countByStatus(String status);
}