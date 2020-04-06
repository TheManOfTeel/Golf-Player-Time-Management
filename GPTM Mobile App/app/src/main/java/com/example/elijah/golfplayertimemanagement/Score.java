package com.example.elijah.golfplayertimemanagement;

public class Score {
    String hole;
    String score;


    public Score(String hole, String score) {
        this.hole = hole;
        this.score = score;
    }

    public String getHole() {
        return hole;
    }

    public void setHole(String hole) {
        this.hole = hole;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "hole='" + hole + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
