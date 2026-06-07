package com.gamehub.demo.dto;

public class GamePlayCount {
    private String gameTitle;
    private long playCount;

    public GamePlayCount(String gameTitle, long playCount) {
        this.gameTitle = gameTitle;
        this.playCount = playCount;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(long playCount) {
        this.playCount = playCount;
    }
}
