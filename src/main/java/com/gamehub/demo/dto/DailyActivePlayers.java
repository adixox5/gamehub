package com.gamehub.demo.dto;

import java.time.LocalDate;

public class DailyActivePlayers {
    private LocalDate date;
    private long playerCount;

    public DailyActivePlayers(LocalDate date, long playerCount) {
        this.date = date;
        this.playerCount = playerCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(long playerCount) {
        this.playerCount = playerCount;
    }
}
