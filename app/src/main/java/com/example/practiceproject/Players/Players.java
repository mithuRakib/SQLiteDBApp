package com.example.practiceproject.Players;

import android.graphics.Bitmap;

public class Players {
    private int playerId;
    private String playerImageUrl;
    private String playerName;
    private String playerJerseyNumber;
    private String playerAge;
    private String playerCategory;

//    public Players(int playerId, String playerImageUrl, String playerName, String playerJerseyNumber, String playerAge, String playerCategory) {
//        this.playerId = playerId;
//        this.playerImageUrl = playerImageUrl;
//        this.playerName = playerName;
//        this.playerJerseyNumber = playerJerseyNumber;
//        this.playerAge = playerAge;
//        this.playerCategory = playerCategory;
//    }
    public Players(String playerImageUrl, String playerName, String playerJerseyNumber, String playerAge, String playerCategory) {
        this.playerImageUrl = playerImageUrl;
        this.playerName = playerName;
        this.playerJerseyNumber = playerJerseyNumber;
        this.playerAge = playerAge;
        this.playerCategory = playerCategory;
    }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public String getPlayerImageUrl() { return playerImageUrl; }
    public void setPlayerImageUrl(String playerImageUrl) { this.playerImageUrl = playerImageUrl; }
    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public String getPlayerJerseyNumber() { return playerJerseyNumber; }
    public void setPlayerJerseyNumber(String playerJerseyNumber) { this.playerJerseyNumber = playerJerseyNumber; }
    public String getPlayerAge() { return playerAge; }
    public void setPlayerAge(String playerAge) { this.playerAge = playerAge; }
    public String getPlayerCategory() { return playerCategory; }
    public void setPlayerCategory(String playerCategory) { this.playerCategory = playerCategory; }
}
