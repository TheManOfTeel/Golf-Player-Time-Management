package com.example.elijah.golfplayertimemanagement;

public class Players {
    String PlayerID;
    String gameID;
    String courseID;


    public Players(String playerID, String gameID, String courseID) {
        PlayerID = playerID;
        this.gameID = gameID;
        this.courseID = courseID;
    }

    public String getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(String playerID) {
        PlayerID = playerID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    @Override
    public String toString() {
        return "Players{" +
                "PlayerID='" + PlayerID + '\'' +
                ", gameID='" + gameID + '\'' +
                ", courseID='" + courseID + '\'' +
                '}';
    }
}
