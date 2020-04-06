package com.example.elijah.golfplayertimemanagement;

public class PlayerHistory {
    String id;
    double totalscore;
    double average;
    String gameID;
    String CourseName;


    public PlayerHistory(String id, double totalscore, double average, String gameID, String courseName) {
        this.id = id;
        this.totalscore = totalscore;
        this.average = average;
        this.gameID = gameID;
        CourseName = courseName;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(double totalscore) {
        this.totalscore = totalscore;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "com.example.elijah.golfplayertimemanagement.SignupActivity.PlayerHistory{" +
                "id='" + id + '\'' +
                ", totalscore=" + totalscore +
                ", average=" + average +
                ", gameID='" + gameID + '\'' +
                ", CourseName='" + CourseName + '\'' +
                '}';
    }
}
