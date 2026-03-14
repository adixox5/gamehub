package com.gamehub.demo.repository;

import com.gamehub.demo.entity.UserLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface UserLoginLogRepository extends JpaRepository<UserLoginLog, Long> {
    List<UserLoginLog> findAllByLoginDateAfter(LocalDateTime date);
}