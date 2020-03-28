package com.example.elijah.golfplayertimemanagement;

public class Game {
    String GameID;
    String CourseID;
    String PlayerID;
    String GroupLeader;
    int Location;
    String TimeStarted;

    public Game(String gameID, String courseID, String playerID, String groupLeader, int location, String timeStarted) {
        GameID = gameID;
        CourseID = courseID;
        PlayerID = playerID;
        GroupLeader = groupLeader;
        Location = location;
        TimeStarted = timeStarted;
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

    public String getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(String playerID) {
        PlayerID = playerID;
    }

    public String getGroupLeader() {
        return GroupLeader;
    }

    public void setGroupLeader(String groupLeader) {
        GroupLeader = groupLeader;
    }

    public int getLocation() {
        return Location;
    }

    public void setLocation(int location) {
        Location = location;
    }

    public String getTimeStarted() {
        return TimeStarted;
    }

    public void setTimeStarted(String timeStarted) {
        TimeStarted = timeStarted;
    }

    @Override
    public String toString() {
        return "Game{" +
                "GameID='" + GameID + '\'' +
                ", CourseID='" + CourseID + '\'' +
                ", PlayerID='" + PlayerID + '\'' +
                ", GroupLeader='" + GroupLeader + '\'' +
                ", Location=" + Location +
                ", TimeStarted='" + TimeStarted + '\'' +
                '}';
    }
}
