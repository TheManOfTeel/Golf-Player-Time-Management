package com.example.elijah.golfplayertimemanagement;

public class Game {
    String GameID;
    String CourseID;
    String PlayerID;

    public Game(String gameID, String courseID) {
        GameID = gameID;
        CourseID = courseID;

    }

    public String getGameID() {
        return GameID;
    }

    public void setGameID(String gameID) {
        GameID = gameID;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }



    @Override
    public String toString() {
        return "Games{" +
                "GameID='" + GameID + '\'' +
                ", CourseID='" + CourseID + '\'' +
                '}';
    }
}
