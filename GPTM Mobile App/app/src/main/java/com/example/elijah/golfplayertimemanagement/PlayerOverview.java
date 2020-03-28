package com.example.elijah.golfplayertimemanagement;

public class PlayerOverview {
    String playerID;
    int currentHoleScore;
    int totalScore;


    public PlayerOverview(String playerID, int currentHoleScore, int totalScore) {
        this.playerID = playerID;
        this.currentHoleScore = currentHoleScore;
        this.totalScore = totalScore;
    }


    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public int getCurrentHoleScore() {
        return currentHoleScore;
    }

    public void setCurrentHoleScore(int currentHoleScore) {
        this.currentHoleScore = currentHoleScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }


    @Override
    public String toString() {
        return "PlayerOverview{" +
                "playerID='" + playerID + '\'' +
                ", currentHoleScore='" + currentHoleScore + '\'' +
                ", totalScore='" + totalScore + '\'' +
                '}';
    }
}
